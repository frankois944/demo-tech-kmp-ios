@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.evaneos.destinationGuide.shared.storage

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


internal actual class DestinationHistoryStorage {

    private var _destinations = MutableStateFlow<List<DestinationHistoryItem>>(emptyList())
    actual val destinations: StateFlow<List<DestinationHistoryItem>> = _destinations.asStateFlow()

    //can be much better!
    private val storedId: MutableList<DestinationHistoryItem> = mutableListOf()

    init {
        _destinations.value = storedId
    }

    actual fun add(destination: DestinationHistoryItem) {
        storedId.add(0, destination)
        _destinations.value = storedId.toList()
    }

    actual fun removeAll() {
        storedId.clear()
        _destinations.value = emptyList()
    }
}