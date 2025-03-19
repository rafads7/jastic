package com.rafaelduransaez.core.navigation

import com.rafaelduransaez.core.navigation.utils.zero
import kotlinx.serialization.Serializable

sealed class NavigationGraphs: JasticNavigable {
    @Serializable
    data object MyJasticGraph: NavigationGraphs()

    @Serializable
    data class MapGraph(
        val latitude: Double = Double.zero,
        val longitude: Double = Double.zero,
        val radiusInMeters: Float = Float.NaN,
    ): NavigationGraphs() {

        fun isEmpty(): Boolean {
            return latitude == Double.zero && longitude == Double.zero
        }

        fun isNotEmpty() = !isEmpty()

        object NavKeys {
            const val KEY_LONGITUDE = "longitude"
            const val KEY_LATITUDE = "latitude"
            const val KEY_ADDRESS = "address"
            const val KEY_RADIUS = "radius"
        }
    }

    @Serializable
    data object SettingsGraph: NavigationGraphs()
}