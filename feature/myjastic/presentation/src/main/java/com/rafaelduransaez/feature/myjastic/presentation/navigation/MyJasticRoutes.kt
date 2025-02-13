package com.rafaelduransaez.feature.myjastic.presentation.navigation

import com.rafaelduransaez.core.navigation.JasticNavigable
import kotlinx.serialization.Serializable

sealed class MyJasticRoutes: JasticNavigable {
    @Serializable
    data object MyJastic: MyJasticRoutes()

    @Serializable
    data object Map: MyJasticRoutes()

    @Serializable
    data class JasticDestinationDetail(val jasticDestinationId: Int): MyJasticRoutes()
}