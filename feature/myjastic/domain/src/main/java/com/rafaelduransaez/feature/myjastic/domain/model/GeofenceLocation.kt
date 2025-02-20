package com.rafaelduransaez.feature.myjastic.domain.model

import com.rafaelduransaez.core.utils.extensions.empty
import com.rafaelduransaez.core.utils.extensions.zero

data class GeofenceLocation(
    val latitude: Double = Double.zero(),
    val longitude: Double = Double.zero(),
    val address: String = String.empty()
)