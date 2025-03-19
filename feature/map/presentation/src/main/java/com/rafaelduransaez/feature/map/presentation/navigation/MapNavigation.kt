package com.rafaelduransaez.feature.map.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.feature.map.presentation.MapScreen
import com.rafaelduransaez.feature.map.presentation.MapViewModel

fun NavGraphBuilder.mapNavGraph(
    onRouteTo: NavRouteTo
) {

    navigation<NavigationGraphs.MapGraph>(startDestination = MapRoutes.Map()) {
        composable<MapRoutes.Map> {

            val viewModel: MapViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            MapScreen(
                uiState = uiState,
                onUiEvent = viewModel::onUiEvent,
                onRouteTo = onRouteTo
            )
        }
    }
}