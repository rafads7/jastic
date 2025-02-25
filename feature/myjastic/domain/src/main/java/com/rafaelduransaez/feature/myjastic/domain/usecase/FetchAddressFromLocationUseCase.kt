package com.rafaelduransaez.feature.myjastic.domain.usecase

import com.rafaelduransaez.core.domain.base.JasticUseCase
import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.core.domain.models.JasticResult
import com.rafaelduransaez.feature.myjastic.domain.model.AddressFetchError
import com.rafaelduransaez.feature.myjastic.domain.repository.LocationRepository
import javax.inject.Inject

class FetchAddressFromLocationUseCase @Inject constructor(
    private val repository: LocationRepository
): JasticUseCase<GeofenceLocation, String, AddressFetchError>() {

    override suspend fun execute(params: GeofenceLocation): JasticResult<String, AddressFetchError> {
        return repository.fetchAddressFromLocation(params)
    }
}