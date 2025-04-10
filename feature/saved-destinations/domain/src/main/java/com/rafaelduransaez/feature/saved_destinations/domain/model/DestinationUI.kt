package com.rafaelduransaez.feature.saved_destinations.domain.model

data class DestinationUI(
    val id: Long,
    val alias: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val radiusInMeters: Float
)