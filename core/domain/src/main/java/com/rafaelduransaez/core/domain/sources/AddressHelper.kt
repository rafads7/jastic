package com.rafaelduransaez.core.domain.sources

interface AddressHelper {
    suspend fun getAddressFromLatLng(latitude: Double, longitude: Double): String
}