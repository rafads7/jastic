package com.rafaelduransaez.feature.map.data.source

import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.base.models.NetworkError

interface AddressHelper {
    suspend fun getAddressFromLatLng(
        latitude: Double,
        longitude: Double
    ): JasticResult<String, NetworkError>
}