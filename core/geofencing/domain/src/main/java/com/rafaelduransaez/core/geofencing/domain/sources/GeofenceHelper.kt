package com.rafaelduransaez.core.geofencing.domain.sources

import com.rafaelduransaez.core.geofencing.domain.JasticGeofence

interface GeofenceHelper {
    fun addGeofence(
        jasticGeofence: JasticGeofence,
        onAdded: () -> Unit = { }
    )

    suspend fun unregisterGeofence(): Result<Unit>
}