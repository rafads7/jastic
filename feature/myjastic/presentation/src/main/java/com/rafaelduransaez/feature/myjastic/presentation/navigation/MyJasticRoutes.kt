package com.rafaelduransaez.feature.myjastic.presentation.navigation

import com.rafaelduransaez.core.navigation.JasticNavigable
import kotlinx.serialization.Serializable

@Serializable
sealed class MyJasticRoutes : JasticNavigable {
    @Serializable
    data object MyJastic : MyJasticRoutes()

    @Serializable
    data class JasticPointDetail(val jasticPointId: Int) : MyJasticRoutes()
}