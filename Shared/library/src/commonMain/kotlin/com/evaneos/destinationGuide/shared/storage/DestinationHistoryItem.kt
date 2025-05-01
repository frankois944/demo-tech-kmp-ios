package com.evaneos.destinationGuide.shared.storage

import kotlinx.serialization.Serializable

@Serializable
internal data class DestinationHistoryItem(
    val id: String, val name: String, val url: String
)