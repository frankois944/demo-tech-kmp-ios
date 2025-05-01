package com.evaneos.destinationGuide.shared.features.destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evaneos.destinationGuide.shared.models.Destination
import com.evaneos.destinationGuide.shared.models.DestinationDetails
import com.evaneos.destinationGuide.shared.services.DestinationFetchingService
import com.evaneos.destinationGuide.shared.services.DestinationFetchingServiceImpl
import com.evaneos.destinationGuide.shared.services.DestinationHistoryService
import com.evaneos.destinationGuide.shared.services.DestinationHistoryServiceImpl
import com.evaneos.destinationGuide.shared.services.GetDestinationDetailsResult
import com.evaneos.destinationGuide.shared.services.GetDestinationsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

public class DestinationsViewModel(
    // better using Injection Framework like Koin
    public val destinationFetchingService: DestinationFetchingService = DestinationFetchingServiceImpl(),
    public val destinationHistoryService: DestinationHistoryService = DestinationHistoryServiceImpl()
) : ViewModel() {

    private val _destinations = MutableStateFlow<List<Destination>>(emptyList())
    public val destinations: StateFlow<List<Destination>> = _destinations

    private val _error = MutableStateFlow<String?>(null)
    public val error: StateFlow<String?> = _error

    public val history: StateFlow<List<DestinationDetails>> =
        destinationHistoryService.current.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        
    init {
        loadDestinations()
    }

    /**
     * Load the list of destinations
     *
     * The result update the collection destinations or error if needed
     */
    public fun loadDestinations() {
        destinationFetchingService.getDestinations {
            when (it) {
                is GetDestinationsResult.Failure -> {
                    _error.value = it.error.name
                }

                is GetDestinationsResult.Success -> {
                    _destinations.value = it.destinations.sortedBy { it.name }
                }
            }
        }
    }
    
    @Throws(Exception::class)
    public suspend fun onSelectDestination(destinationId: String): DestinationDetails {
        return withContext(Dispatchers.Default) {
            suspendCoroutine { continuation ->
                destinationFetchingService.getDestinationDetails(destinationId) {
                    when (it) {
                        is GetDestinationDetailsResult.Failure -> {
                            continuation.resumeWithException(
                                Exception(it.error.name)
                            )
                        }

                        is GetDestinationDetailsResult.Success -> {
                            destinationHistoryService.addToHistory(it.destinationDetails)
                            continuation.resume(it.destinationDetails)
                        }
                    }
                }
            }
        }
    }
    
    public fun addDestinationDetailToHistory(destinationDetails: DestinationDetails) {
        destinationHistoryService.addToHistory(destinationDetails)
    }

}
