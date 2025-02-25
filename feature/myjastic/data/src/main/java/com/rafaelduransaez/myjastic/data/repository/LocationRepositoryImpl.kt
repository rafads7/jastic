package com.rafaelduransaez.myjastic.data.repository

import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.core.domain.models.JasticResult
import com.rafaelduransaez.core.domain.models.JasticResult.Companion.failure
import com.rafaelduransaez.core.domain.models.JasticResult.Companion.success
import com.rafaelduransaez.core.domain.sources.AddressHelper
import com.rafaelduransaez.core.domain.sources.LocationHelper
import com.rafaelduransaez.feature.myjastic.domain.model.AddressFetchError
import com.rafaelduransaez.feature.myjastic.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val locationHelper: LocationHelper,
    private val addressHelper: AddressHelper
) : LocationRepository {

    override suspend fun fetchCurrentLocation(): Flow<GeofenceLocation?> {
        return locationHelper.requestCurrentLocation()
    }

    override suspend fun fetchAddressFromLocation(params: GeofenceLocation): JasticResult<String, AddressFetchError> {
        return withContext(dispatcher) {
            try {
                JasticResult.success(addressHelper.getAddressFromLatLng(params.latitude,params.longitude))
            } catch (e: IOException) {
                val failure = when {
                    e.message.isNullOrEmpty() -> AddressFetchError.UnknownError
                    e.message.orEmpty()
                        .contains("network", ignoreCase = true) -> AddressFetchError.NetworkError

                    e.message.orEmpty()
                        .contains("not found", ignoreCase = true) -> AddressFetchError.NotFound

                    else -> AddressFetchError.UnknownError
                }
                JasticResult.failure(failure)
            }
        }
    }
}
