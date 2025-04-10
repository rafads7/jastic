package com.rafaelduransaez.feature.saved_destinations.domain.model

data class DestinationsListItemUI(
    val id: Long,
    val alias: String,
    val address: String,
    val radiusInMeters: Float
)