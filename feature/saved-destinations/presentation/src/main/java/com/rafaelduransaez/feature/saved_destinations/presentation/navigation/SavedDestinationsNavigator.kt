package com.rafaelduransaez.feature.saved_destinations.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.Back
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs.SavedDestinationsGraph
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.core.permissions.OnPermissionNeeded
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationUI
import com.rafaelduransaez.feature.saved_destinations.presentation.navigation.SavedDestinationsRoutes.SavedDestinations
import com.rafaelduransaez.feature.saved_destinations.presentation.screen.SavedDestinationsNavState
import com.rafaelduransaez.feature.saved_destinations.presentation.screen.SavedDestinationsScreen
import com.rafaelduransaez.feature.saved_destinations.presentation.screen.SavedDestinationsViewModel

fun NavGraphBuilder.savedDestinationsGraph(
    onRouteTo: NavRouteTo,
    onPermissionNeeded: OnPermissionNeeded
) {

    navigation<SavedDestinationsGraph>(startDestination = SavedDestinations) {
        composable<SavedDestinations> {
            val viewModel = hiltViewModel<SavedDestinationsViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            SavedDestinationsScreen(
                uiState = state,
                navState = viewModel.navState,
                onUiEvent = viewModel::onUiEvent,
                onRouteTo = onRouteTo,
                onPermissionNeeded = onPermissionNeeded
            )
        }
    }
}