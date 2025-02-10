package com.rafaelduransaez.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationRoutes {
    @Serializable
    data object MyJastic: NavigationRoutes

    @Serializable
    data object Settings: NavigationRoutes

    @Serializable
    data object Map: NavigationRoutes

    @Serializable
    data class JasticDestinationDetail(val jasticDestinationId: Int): NavigationRoutes
}




