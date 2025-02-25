package com.rafaelduransaez.core.domain.models

import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.zero
import kotlinx.serialization.Serializable

@Serializable
data class GeofenceLocation(
    val latitude: Double = Double.zero(),
    val longitude: Double = Double.zero(),
    val address: String = String.empty()
)