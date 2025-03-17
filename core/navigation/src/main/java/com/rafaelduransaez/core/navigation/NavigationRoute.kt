@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.rafaelduransaez.core.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

interface JasticNavigable

@Serializable
data object Back: JasticNavigable

@Serializable
sealed interface NavigationRoute : JasticNavigable {

    @Serializable
    data object Settings : NavigationRoute
}




