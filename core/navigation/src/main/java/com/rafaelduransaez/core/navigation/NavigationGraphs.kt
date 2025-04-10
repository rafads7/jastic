package com.rafaelduransaez.core.navigation

import com.rafaelduransaez.core.domain.extensions.zero
import kotlinx.serialization.Serializable

sealed class NavigationGraphs : JasticNavigable {
    @Serializable
    data object MyJasticGraph : NavigationGraphs()

    @Serializable
    data object SavedDestinationsGraph : NavigationGraphs()

    @Serializable
    data class MapGraph(
        val id: Long = Long.zero,
        val latitude: Double = Double.zero,
        val longitude: Double = Double.zero,
        val radiusInMeters: Float = Float.NaN,
    ) : NavigationGraphs() {

        private fun hasNoCoordinates() = latitude == Double.zero && longitude == Double.zero


        private fun hasNoId() = id == Long.zero

        fun isEmpty() = hasNoId() && hasNoCoordinates()

        fun isNotEmpty() = !isEmpty()

        object NavKeys {
            const val KEY_LONGITUDE = "longitude"
            const val KEY_LATITUDE = "latitude"
            const val KEY_ADDRESS = "address"
            const val KEY_RADIUS = "radius"
        }
    }

    @Serializable
    data object SettingsGraph : NavigationGraphs()
}