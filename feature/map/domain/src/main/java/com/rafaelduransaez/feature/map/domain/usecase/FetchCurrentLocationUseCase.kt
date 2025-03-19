package com.rafaelduransaez.feature.map.domain.usecase

import com.rafaelduransaez.core.domain.base.JasticFlowUseCase
import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.feature.map.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCurrentLocationUseCase @Inject constructor(
    private val repository: LocationRepository,
) : JasticFlowUseCase<Unit, GeofenceLocation?>() {

    override suspend fun execute(params: Unit): Flow<GeofenceLocation?> =
        repository.fetchCurrentLocation()
}