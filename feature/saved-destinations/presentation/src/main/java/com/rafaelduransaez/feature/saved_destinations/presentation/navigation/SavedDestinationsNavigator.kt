package com.rafaelduransaez.feature.saved_destinations.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs.SavedDestinationsGraph
import com.rafaelduransaez.core.ui.permissions.OnPermissionNeeded
import com.rafaelduransaez.feature.saved_destinations.domain.SavedDestination
import com.rafaelduransaez.feature.saved_destinations.presentation.navigation.SavedDestinationsRoutes.SavedDestinations
import com.rafaelduransaez.feature.saved_destinations.presentation.screen.SavedDestinationsScreen

fun NavGraphBuilder.savedDestinationsGraph(
    onRouteTo: NavRouteTo,
    onPermissionNeeded: OnPermissionNeeded
) {

    navigation<SavedDestinationsGraph>(startDestination = SavedDestinations) {
        composable<SavedDestinations> {
            SavedDestinationsScreen(
                savedDestinations = mockList,
                onRouteTo = onRouteTo,
                onPermissionNeeded = onPermissionNeeded
            )
        }
    }
}

val mockList = listOf(
    SavedDestination(
        id = 1,
        alias = "Santiago Bernabéu",
        address = "Calle de la Castellana, 28012 Madrid, Spain",
        latitude = 40.453053,
        longitude = -3.688344,
        radiusInMeters = 100f
    ),
    SavedDestination(
        id = 2,
        alias = "Jiading Tongji Daxue",
        address = "Street I do not know the name where but is in Jiading, Shanghai, China",
        latitude = 31.2822,
        longitude = 121.2120,
        radiusInMeters = 700f
    )
)