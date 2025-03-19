package com.rafaelduransaez.feature.saved_destinations.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs.SavedDestinationsGraph
import com.rafaelduransaez.feature.saved_destinations.domain.SavedDestination
import com.rafaelduransaez.feature.saved_destinations.presentation.navigation.SavedDestinationsRoutes.SavedDestinations
import com.rafaelduransaez.feature.saved_destinations.presentation.screen.SavedDestinationsScreen

fun NavGraphBuilder.savedDestinationsGraph(
    onRouteTo: NavRouteTo
) {

    navigation<SavedDestinationsGraph>(startDestination = SavedDestinations) {
        composable<SavedDestinations> {
            SavedDestinationsScreen(savedDestinations = mockList, onRouteTo = onRouteTo)
        }
    }
}

val mockList = listOf(
    SavedDestination(
        id = 1,
        alias = "Home",
        address = "Calle de la Rosa, 1, 28012 Madrid, Spain",
        latitude = 0.0,
        longitude = 0.0,
        radiusInMeters = 100f
    ),
    SavedDestination(
        id = 2,
        alias = "Work",
        address = "Calle de la Rosa, 2, 28012 Madrid, Spain",
        latitude = 0.0,
        longitude = 0.0,
        radiusInMeters = 200f
    ),
    SavedDestination(
        id = 3,
        alias = "Gym",
        address = "Calle de la Rosa, 3, 28012 Madrid, Spain",
        latitude = 0.0,
        longitude = 0.0,
        radiusInMeters = 300f
    ),
)