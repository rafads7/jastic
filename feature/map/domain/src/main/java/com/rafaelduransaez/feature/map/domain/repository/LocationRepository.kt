package com.rafaelduransaez.feature.map.domain.repository

import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.base.models.NetworkError
import com.rafaelduransaez.feature.map.domain.model.GeofenceLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun fetchCurrentLocation(): Flow<GeofenceLocation?>
    suspend fun fetchAddressFromLocation(location: GeofenceLocation): JasticResult<String, NetworkError>
}