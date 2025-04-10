package com.rafaelduransaez.feature.myjastic.domain.model

import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.zero

data class DestinationUI(
    val id: Long = Long.zero,
    val alias: String = String.empty,
    val address: String = String.empty,
    val latitude: Double = Double.zero,
    val longitude: Double = Double.zero,
    val radiusInMeters: Float = Float.NaN
)
