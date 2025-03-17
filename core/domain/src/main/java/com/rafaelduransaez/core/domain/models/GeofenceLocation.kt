package com.rafaelduransaez.core.domain.models

import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.zero
import kotlinx.serialization.Serializable

@Serializable
data class GeofenceLocation(
    val latitude: Double = Double.zero,
    val longitude: Double = Double.zero,
    val address: String = String.empty,
    val radiusInMeters: Float = GEOFENCE_DEFAULT_RADIUS
)

const val GEOFENCE_DEFAULT_RADIUS = 200f