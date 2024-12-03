package com.rafaelduransaez.domain.models

data class Geofence(
    val requestKey: Int,
    val latitude: Double,
    val longitude: Double,
    val radius: Int = PRECISE_RADIUS,
    val expirationDuration: Long ?= null,
    val transitionTypes: Int = 0
) {
    companion object {
        const val PRECISE_RADIUS = 100
        const val FINE_RADIUS = 500
        const val COARSE_RADIUS = 1000
    }
}