package com.rafaelduransaez.feature.myjastic.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs.MyJasticGraph
import com.rafaelduransaez.core.ui.permissions.OnPermissionNeeded
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailScreen
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.LocationSelected
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailViewModel
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticScreen
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticViewModel
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes.JasticPointDetail
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes.MyJastic
import com.rafaelduransaez.feature.myjastic.presentation.utils.toGeofenceLocation

fun NavGraphBuilder.myJasticNavGraph(
    onRouteTo: NavRouteTo,
    onPermissionNeeded: OnPermissionNeeded
) {

    navigation<MyJasticGraph>(startDestination = MyJastic) {
        composable<MyJastic> {
            val viewModel = hiltViewModel<MyJasticViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            MyJasticScreen(
                uiState = uiState,
                onRouteTo = onRouteTo
            )
        }

        composable<JasticPointDetail> { currentBackStackEntry ->
            val viewModel: JasticPointDetailViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            with(currentBackStackEntry.savedStateHandle) {
                LaunchedEffect(this) {
                    if (keys().isNotEmpty()) {
                        val location = this@with.toGeofenceLocation()
                        viewModel.onUiEvent(LocationSelected(location))
                        /*
                        val data = getAndRemove<MapNavData>(KEY_DATA, MapNavData.Empty)
                        when (data) {
                            is MapNavData.Location ->
                                viewModel.onUiEvent(LocationSelected(data.toGeofenceLocation()))
                            MapNavData.Empty -> Unit
                        }
                        */
                    }
                }
            }

            JasticPointDetailScreen(
                uiState = uiState,
                onUiEvent = viewModel::onUiEvent,
                onRouteTo = onRouteTo,
                onPermissionNeeded = onPermissionNeeded
            )
        }
    }
}
