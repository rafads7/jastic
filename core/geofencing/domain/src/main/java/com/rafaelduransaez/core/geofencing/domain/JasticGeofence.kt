package com.rafaelduransaez.core.geofencing.domain

data class JasticGeofence(
    val requestKey: String,
    val latitude: Double,
    val longitude: Double,
    val radiusInMeters: Float
)