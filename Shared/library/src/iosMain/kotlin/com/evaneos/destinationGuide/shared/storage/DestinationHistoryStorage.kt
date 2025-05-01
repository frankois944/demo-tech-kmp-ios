@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.evaneos.destinationGuide.shared.storage

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults

internal actual class DestinationHistoryStorage {

    private var _destinations = MutableStateFlow<List<DestinationHistoryItem>>(emptyList())
    actual val destinations: StateFlow<List<DestinationHistoryItem>> = _destinations.asStateFlow()

    //can be much better!
    private val storedId: MutableList<DestinationHistoryItem>
        get() {
            return NSUserDefaults.standardUserDefaults.stringForKey("destinationIds")?.let {
                Json.decodeFromString<List<DestinationHistoryItem>>(it).toMutableList()
            } ?: mutableListOf()
        }

    init {
        _destinations.value = storedId
    }

    actual fun add(destination: DestinationHistoryItem) {
        val ids = storedId
        ids.add(0, destination)
        NSUserDefaults.standardUserDefaults.setObject(
            Json.encodeToString(ids),
            "destinationIds"
        )
        _destinations.value = ids.toList()
    }

    actual fun removeAll() {
        NSUserDefaults.standardUserDefaults.removeObjectForKey("destinationIds")
        _destinations.value = emptyList()
    }
}