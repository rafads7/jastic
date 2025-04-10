package com.rafaelduransaez.feature.map.domain.model

import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.isNotZero
import com.rafaelduransaez.core.domain.extensions.zero
import kotlinx.serialization.Serializable

@Serializable
data class GeofenceLocation(
    val latitude: Double = Double.zero,
    val longitude: Double = Double.zero,
    val address: String = String.empty,
    val radiusInMeters: Float = GEOFENCE_DEFAULT_RADIUS
) {
    val isInitialized: Boolean
        get() = address.isNotEmpty() && latitude.isNotZero() && longitude.isNotZero()
}

const val GEOFENCE_DEFAULT_RADIUS = 200f