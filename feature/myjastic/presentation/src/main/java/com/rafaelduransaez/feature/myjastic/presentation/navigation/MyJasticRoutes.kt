package com.rafaelduransaez.feature.myjastic.presentation.navigation

import android.os.Parcelable
import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.zero
import com.rafaelduransaez.core.navigation.JasticNavData
import com.rafaelduransaez.core.navigation.JasticNavigable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed class MyJasticRoutes : JasticNavigable {
    @Serializable
    data object MyJastic : MyJasticRoutes()

    @Serializable
    data class Map(
        val latitude: Double = Double.zero,
        val longitude: Double = Double.zero,
        val radiusInMeters: Float = Float.NaN,
        //val data: MapNavData = MapNavData.Empty
    ) : MyJasticRoutes() {
        fun isEmpty(): Boolean {
            return latitude == Double.zero() && longitude == Double.zero()
        }

        fun isNotEmpty() = !isEmpty()
    }

    @Serializable
    data class JasticDestinationDetail(val jasticDestinationId: Int) : MyJasticRoutes()
}

@Parcelize
sealed interface MapNavData : JasticNavData, Parcelable {

    @Parcelize
    @Serializable
    data object Empty : MapNavData, Parcelable

    @Parcelize
    data class Location(
        val latitude: Double = Double.zero,
        val longitude: Double = Double.zero,
        val address: String = String.empty,
        val radiusInMeters: Float = Float.NaN
    ) : MapNavData, Parcelable
}