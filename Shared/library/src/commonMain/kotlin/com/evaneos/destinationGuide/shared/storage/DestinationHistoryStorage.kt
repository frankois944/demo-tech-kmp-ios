@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.evaneos.destinationGuide.shared.storage

import kotlinx.coroutines.flow.StateFlow

// both iOS android are implemented, other target can also be added
internal expect class DestinationHistoryStorage() {

    // store the id of the destination
    // could be the whole model instead of the id
    val destinations: StateFlow<List<DestinationHistoryItem>>

    fun add(destination: DestinationHistoryItem)

    fun removeAll()
}