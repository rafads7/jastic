package com.rafaelduransaez.feature.map.presentation.navigation

import com.rafaelduransaez.core.domain.extensions.zero
import com.rafaelduransaez.core.navigation.JasticNavigable
import kotlinx.serialization.Serializable

@Serializable
sealed interface MapRoutes : JasticNavigable {

    @Serializable
    data class Map(
        val latitude: Double = Double.zero,
        val longitude: Double = Double.zero,
        val radiusInMeters: Float = Float.NaN,
        //val data: MapNavData = MapNavData.Empty
    ) : MapRoutes {

        fun isEmpty(): Boolean {
            return latitude == Double.zero() && longitude == Double.zero()
        }

        fun isNotEmpty() = !isEmpty()
    }
}