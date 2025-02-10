package com.rafaelduransaez.core.navigation

import kotlinx.serialization.Serializable

sealed class NavigationGraphs {
    @Serializable
    data object MyJasticGraph: NavigationGraphs()

    @Serializable
    data object SettingsGraph: NavigationGraphs()
}