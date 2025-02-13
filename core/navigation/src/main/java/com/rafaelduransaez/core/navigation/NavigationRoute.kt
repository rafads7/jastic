package com.rafaelduransaez.core.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.serialization.Serializable

interface JasticNavigable

@Serializable
data object Back: JasticNavigable

@Serializable
sealed interface NavigationRoute: JasticNavigable {

    @Serializable
    data object Settings: NavigationRoute
}

typealias NavRouteTo = (JasticNavigable, NavOptionsBuilder.() -> Unit) -> Unit




