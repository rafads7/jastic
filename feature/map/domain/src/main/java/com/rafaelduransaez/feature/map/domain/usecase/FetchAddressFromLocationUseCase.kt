package com.rafaelduransaez.feature.map.domain.usecase

import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.base.models.NetworkError
import com.rafaelduransaez.core.base.usecase.JasticUseCase
import com.rafaelduransaez.feature.map.domain.model.GeofenceLocation
import com.rafaelduransaez.feature.map.domain.repository.LocationRepository
import javax.inject.Inject

class FetchAddressFromLocationUseCase @Inject constructor(
    private val repository: LocationRepository
): JasticUseCase<GeofenceLocation, String, NetworkError>() {

    override suspend fun execute(params: GeofenceLocation): JasticResult<String, NetworkError> {
        return repository.fetchAddressFromLocation(params)
    }
}
