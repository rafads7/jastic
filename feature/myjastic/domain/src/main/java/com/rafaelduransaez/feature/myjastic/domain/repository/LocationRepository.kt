package com.rafaelduransaez.feature.myjastic.domain.repository

import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.core.domain.models.JasticResult
import com.rafaelduransaez.core.domain.models.NetworkError
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun fetchCurrentLocation(): Flow<GeofenceLocation?>
    suspend fun fetchAddressFromLocation(location: GeofenceLocation): JasticResult<String, NetworkError>
}