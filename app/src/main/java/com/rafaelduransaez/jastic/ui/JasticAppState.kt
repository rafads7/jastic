package com.rafaelduransaez.jastic.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import com.rafaelduransaez.core.components.jToolbar.JToolbarConfig
import com.rafaelduransaez.core.components.jToolbar.JToolbarController
import com.rafaelduransaez.core.components.jToolbar.defaultToolbarConfig
import com.rafaelduransaez.core.domain.extensions.isFalse
import com.rafaelduransaez.core.navigation.JasticNavigable
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.utils.navigateTo
import com.rafaelduransaez.jastic.hasRouteInHierarchy
import com.rafaelduransaez.jastic.navigation.TopLevelRoute
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberJasticAppState(
    navHostController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): JasticAppState {

    return remember(navHostController, coroutineScope) {
        JasticAppState(
            navController = navHostController,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
class JasticAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {

    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry =
                navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val topLevelDestinations = listOf(
        TopLevelRoute.MyJasticRoute,
        TopLevelRoute.SavedDestinationsRoute,
        TopLevelRoute.SettingsRoute
    )

    val bottomBarIsVisible: Boolean
        @Composable get() = currentDestination?.hasRouteInHierarchy<NavigationGraphs.MapGraph>()
            .isFalse()

    private val _toolbarConfig = mutableStateOf(defaultToolbarConfig)
    var toolbarConfig: JToolbarConfig by _toolbarConfig
        private set

    val toolbarController: JToolbarController = object : JToolbarController {
        override fun setToolbar(config: JToolbarConfig) {
            if (toolbarConfig != config) {
                toolbarConfig = config
            }
        }

        override fun resetToolbarWhenNeeded() {
            if (toolbarConfig != defaultToolbarConfig) {
                toolbarConfig = defaultToolbarConfig
            }
        }
    }

    fun onTopLevelRouteClicked(route: NavigationGraphs) {
        with(navController) {
            navigate(route = route) {
                popUpTo(graph.id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    fun navigateTo(
        route: JasticNavigable,
        navData: Map<String, Any>,
        options: NavOptionsBuilder.() -> Unit
    ) {
        navController.navigateTo(route, navData, options)
    }
}