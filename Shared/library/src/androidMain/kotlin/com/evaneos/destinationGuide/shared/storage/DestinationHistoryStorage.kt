@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.evaneos.destinationGuide.shared.storage

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


internal actual class DestinationHistoryStorage {

    private var _currentDestinationIds = MutableStateFlow<List<String>>(emptyList())
    actual val currentDestinationIds: StateFlow<List<String>> = _currentDestinationIds.asStateFlow()

    //can be much better!
    private val storedId: MutableList<String> = mutableListOf()

    init {
        _currentDestinationIds.value = storedId
    }

    actual fun addDestinationId(destinationId: String) {
        storedId.add(0, destinationId)
        _currentDestinationIds.value = storedId.toList()
    }

    actual fun removeAll() {
        storedId.clear()
        _currentDestinationIds.value = emptyList()
    }
}