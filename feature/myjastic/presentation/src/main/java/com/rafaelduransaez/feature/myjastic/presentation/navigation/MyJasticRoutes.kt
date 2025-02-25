package com.rafaelduransaez.feature.myjastic.presentation.navigation

import com.rafaelduransaez.core.domain.extensions.zero
import com.rafaelduransaez.core.navigation.JasticNavigable
import kotlinx.serialization.Serializable

sealed class MyJasticRoutes : JasticNavigable {
    @Serializable
    data object MyJastic : MyJasticRoutes()

    @Serializable
    data class Map(val latitude: Double = Double.zero(), val longitude: Double = Double.zero()) :
        MyJasticRoutes() {
            fun isEmpty(): Boolean {
                return latitude == Double.zero() && longitude == Double.zero()
            }
        }

    @Serializable
    data class JasticDestinationDetail(val jasticDestinationId: Int) : MyJasticRoutes()
}