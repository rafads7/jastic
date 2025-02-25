package com.rafaelduransaez.core.domain.sources

import com.rafaelduransaez.core.domain.models.GeofenceLocation
import kotlinx.coroutines.flow.Flow

interface LocationHelper {
    fun requestLocationUpdates(): Flow<GeofenceLocation?>
    suspend fun requestCurrentLocation(): Flow<GeofenceLocation?>
}