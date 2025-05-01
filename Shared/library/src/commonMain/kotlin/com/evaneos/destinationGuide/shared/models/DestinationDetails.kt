package com.evaneos.destinationGuide.shared.models

// there is a reason why we use don't use a data class (guess?...)
public class DestinationDetails(
    public val id: String,
    public val name: String,
    public val url: String
)
