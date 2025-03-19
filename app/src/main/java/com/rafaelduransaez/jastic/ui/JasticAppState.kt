package com.rafaelduransaez.jastic.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import com.rafaelduransaez.core.navigation.JasticNavigable
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.navigateTo
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
                navController.currentBackStackEntryFlow.collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val topLevelDestinations = listOf(
        TopLevelRoute.MyJasticRoute,
        TopLevelRoute.SettingsRoute,
        TopLevelRoute.SavedDestinationsRoute
    )

    /*    val showFAB: Boolean
            @Composable get() {
                return currentDestination?.isStartDestinationOf(NavigationGraphs.MyJasticGraph) == true
            }


        fun NavDestination.isStartDestinationOf(subGraph: NavigationGraphs): Boolean {
            return when (subGraph) {
                NavigationGraphs.MyJasticGraph -> {
                    this.route == subGraph.findStartDestination().route
                }

                else -> false
            }
        }*/

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

    fun navigateTo(route: JasticNavigable, navData: Map<String, Any>, options: NavOptionsBuilder.() -> Unit) {
        navController.navigateTo(route, navData, options)
    }
}