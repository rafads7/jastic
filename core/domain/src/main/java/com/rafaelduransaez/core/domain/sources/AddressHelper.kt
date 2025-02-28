package com.rafaelduransaez.core.domain.sources

import com.rafaelduransaez.core.domain.models.JasticResult
import com.rafaelduransaez.core.domain.models.NetworkError

interface AddressHelper {
    suspend fun getAddressFromLatLng(
        latitude: Double,
        longitude: Double
    ): JasticResult<String, NetworkError>
}