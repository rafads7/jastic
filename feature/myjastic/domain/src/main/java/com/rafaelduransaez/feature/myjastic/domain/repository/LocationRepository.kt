package com.rafaelduransaez.feature.myjastic.domain.repository

import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.core.domain.models.JasticResult
import com.rafaelduransaez.feature.myjastic.domain.model.AddressFetchError
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun fetchCurrentLocation(): Flow<GeofenceLocation?>
    suspend fun fetchAddressFromLocation(params: GeofenceLocation): JasticResult<String, AddressFetchError>
}