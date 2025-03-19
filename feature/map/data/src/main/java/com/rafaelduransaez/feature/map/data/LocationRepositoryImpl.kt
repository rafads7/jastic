package com.rafaelduransaez.feature.map.data

import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.core.domain.models.JasticResult
import com.rafaelduransaez.core.domain.models.JasticResult.Companion.failure
import com.rafaelduransaez.core.domain.models.NetworkError
import com.rafaelduransaez.core.domain.sources.AddressHelper
import com.rafaelduransaez.core.domain.sources.LocationHelper
import com.rafaelduransaez.feature.map.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationHelper: LocationHelper,
    private val addressHelper: AddressHelper
) : LocationRepository {

    override suspend fun fetchCurrentLocation(): Flow<GeofenceLocation?> {
        return locationHelper.requestCurrentLocation()
    }

    override suspend fun fetchAddressFromLocation(location: GeofenceLocation): JasticResult<String, NetworkError> {
        return try {
            addressHelper.getAddressFromLatLng(location.latitude, location.longitude)
        } catch (e: Exception) {
            JasticResult.failure(NetworkError.UNKNOWN)
        }
    }
}

