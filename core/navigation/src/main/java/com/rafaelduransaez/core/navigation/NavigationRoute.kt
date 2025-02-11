package com.rafaelduransaez.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationRoute {

    sealed class Home: NavigationRoute {
        @Serializable
        data object MyJastic: Home()

        @Serializable
        data object Map: Home()

        @Serializable
        data class JasticDestinationDetail(val jasticDestinationId: Int): Home()
    }

    @Serializable
    data object Settings: NavigationRoute
}

typealias NavRouteTo = (NavigationRoute) -> Unit




