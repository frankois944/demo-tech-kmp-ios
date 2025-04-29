@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.evaneos.destinationGuide.shared.storage

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.Foundation.NSUserDefaults

internal actual class DestinationHistoryStorage {

    private var _currentDestinationIds = MutableStateFlow<List<String>>(emptyList())
    actual val currentDestinationIds: StateFlow<List<String>> = _currentDestinationIds.asStateFlow()

    //can be much better!
    private val storedId: MutableList<String>
        get() {
            val list = NSUserDefaults.standardUserDefaults.arrayForKey("destinationIds") as? List<String>
            return list?.toMutableList() ?: mutableListOf()
        }

    init {
        _currentDestinationIds.value = storedId
    }

    actual fun addDestinationId(destinationId: String) {
        val ids = storedId
        ids.add(0, destinationId)
        NSUserDefaults.standardUserDefaults.setObject(ids, "destinationIds")
        _currentDestinationIds.value = ids.toList()
    }

    actual fun removeAll() {
        NSUserDefaults.standardUserDefaults.removeObjectForKey("destinationIds")
        _currentDestinationIds.value = emptyList()
    }
}