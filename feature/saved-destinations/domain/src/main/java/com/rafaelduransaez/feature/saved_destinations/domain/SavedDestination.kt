package com.rafaelduransaez.feature.saved_destinations.domain

data class SavedDestination(
    val id: Int,
    val alias: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val radiusInMeters: Float
)