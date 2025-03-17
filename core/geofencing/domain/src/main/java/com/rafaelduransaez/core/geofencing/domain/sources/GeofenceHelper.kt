package com.rafaelduransaez.core.geofencing.domain.sources

interface GeofenceHelper {
    fun addGeofence(
        key: String,
        latitude: Double,
        longitude: Double,
        radiusInMeters: Float,
        expirationTimeInMillis: Long,
        onAdded: () -> Unit = { }
    )

    suspend fun unregisterGeofence(): Result<Unit>
}