package com.rafaelduransaez.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.NavigationRoute
import com.rafaelduransaez.feature.settings.presentation.settings.SettingsSection

/*interface SettingsNavigator: FeatureNavigator {
    fun NavHostController.navigateToSettings()
}

class SettingsNavigatorImpl: SettingsNavigator {

    override fun NavHostController.navigateToSettings() {
        navigate(NavigationGraphs.SettingsGraph)
    }

    override fun NavGraphBuilder.registerNavGraph(navHostController: NavHostController) {
        navigation<NavigationGraphs.SettingsGraph>(startDestination = NavigationRoutes.Settings) {
            val x = rememberNavController()
            composable<NavigationRoutes.Settings> {
                val y = rememberNavController()
                ScreenFake()
            }
        }
    }
}*/

fun NavController.navigateToSettings() {
    navigate(route = NavigationRoute.Settings)
}

fun NavGraphBuilder.settingsGraph() {
    navigation<NavigationGraphs.SettingsGraph>(startDestination = NavigationRoute.Settings) {

        composable<NavigationRoute.Settings> {
            val innerNavController = rememberNavController()
            SettingsSection(innerNavController)
        }
    }
}
