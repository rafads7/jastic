package com.rafaelduransaez.feature.map.data.source

import com.rafaelduransaez.feature.map.domain.model.GeofenceLocation
import kotlinx.coroutines.flow.Flow

interface LocationHelper {
    fun requestLocationUpdates(): Flow<GeofenceLocation?>
    suspend fun requestCurrentLocation(): Flow<GeofenceLocation?>
}