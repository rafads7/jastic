package com.rafaelduransaez.jastic

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.rafaelduransaez.core.navigation.Back
import com.rafaelduransaez.core.navigation.JasticNavigable
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.jastic.navigation.TopLevelRoute

inline fun <reified T : NavigationGraphs> NavDestination?.isInATopLevelDestinationHierarchy(
    topLevelDestinations:  List<TopLevelRoute<out T>>
): Boolean {
    //return topLevelDestinations.any { this?.hasRoute(it.route::class) == true }
    return this?.hierarchy?.any { destination ->
        topLevelDestinations.any { it.navRoute::class.simpleName == destination.route }
    } == true
}


inline fun <reified T : JasticNavigable> NavDestination.hasRouteInHierarchy(): Boolean {
    return this.hierarchy.any { it.hasRoute(T::class) }
}

fun NavHostController.navigateTo(
    route: JasticNavigable,
    navData: Map<String, Any>,
    //navData: JasticNavData,
    options: NavOptionsBuilder.() -> Unit
) {
    navData.forEach { previousBackStackEntry?.savedStateHandle?.set(it.key, it.value)}
    //previousBackStackEntry?.savedStateHandle?.set(KEY_DATA, navData)

    when (route) {
        Back -> navigateUp()
        else -> navigate(route, navOptions(options))
    }
}

const val KEY_DATA = "data"