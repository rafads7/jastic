package com.rafaelduransaez.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.feature.settings.presentation.navigation.SettingsRoutes
import com.rafaelduransaez.feature.settings.presentation.settings.SettingsSection

fun NavGraphBuilder.settingsGraph() {
    navigation<NavigationGraphs.SettingsGraph>(startDestination = SettingsRoutes.Settings) {

        composable<SettingsRoutes.Settings> {
            val innerNavController = rememberNavController()
            SettingsSection(innerNavController)
        }
    }
}
