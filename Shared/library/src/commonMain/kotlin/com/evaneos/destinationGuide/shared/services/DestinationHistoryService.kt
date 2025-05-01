@file:OptIn(ExperimentalCoroutinesApi::class)

package com.evaneos.destinationGuide.shared.services

import com.evaneos.destinationGuide.shared.models.Destination
import com.evaneos.destinationGuide.shared.models.DestinationDetails
import com.evaneos.destinationGuide.shared.storage.DestinationHistoryItem
import com.evaneos.destinationGuide.shared.storage.DestinationHistoryStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

public interface DestinationHistoryService {

    /**
     * Observe the history of destinations using Flow
     */
    public val current: Flow<List<DestinationDetails>>

    /**
     * Add to history
     *
     * @param destination
     */
    public fun addToHistory(destination: DestinationDetails)

    /**
     * Clear the history
     */
    public fun clear()
}

public class DestinationHistoryServiceImpl : DestinationHistoryService {

    private val storage = DestinationHistoryStorage()
    private val scope = CoroutineScope(Dispatchers.Default)

    private var _current = MutableStateFlow<List<DestinationDetails>>(emptyList())
    override val current: Flow<List<DestinationDetails>> = _current

    init {
        scope.launch {
            storage.destinations.collectLatest {
                _current.value = it.mapNotNull {
                    destinationDetailsStub.firstOrNull { destination -> destination.id == it.id }
                }
            }
        }
    }

    override fun addToHistory(destination: DestinationDetails) {
        storage.add(
            DestinationHistoryItem(
                destination.id,
                destination.name,
                destination.url
            )
        )
    }

    override fun clear() {
        storage.removeAll()
    }
}