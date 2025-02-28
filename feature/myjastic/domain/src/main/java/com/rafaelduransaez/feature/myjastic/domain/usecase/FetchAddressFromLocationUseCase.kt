package com.rafaelduransaez.feature.myjastic.domain.usecase

import com.rafaelduransaez.core.domain.base.JasticUseCase
import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.core.domain.models.JasticResult
import com.rafaelduransaez.core.domain.models.NetworkError
import com.rafaelduransaez.feature.myjastic.domain.repository.LocationRepository
import javax.inject.Inject

class FetchAddressFromLocationUseCase @Inject constructor(
    private val repository: LocationRepository
): JasticUseCase<GeofenceLocation, String, NetworkError>() {

    override suspend fun execute(params: GeofenceLocation): JasticResult<String, NetworkError> {
        return repository.fetchAddressFromLocation(params)
    }
}