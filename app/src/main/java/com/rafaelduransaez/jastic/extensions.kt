package com.rafaelduransaez.jastic

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
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