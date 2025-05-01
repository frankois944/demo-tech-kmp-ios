package com.evaneos.destinationGuide.shared.models

// there is a reason why we use don't use a data class (guess?...)
public class Destination(
    public val id: String,
    public val name: String,
    public val picture: String,
    public val tag: String?,
    public val rating: Int
)
