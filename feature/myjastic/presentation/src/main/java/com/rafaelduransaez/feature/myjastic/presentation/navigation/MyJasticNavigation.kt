package com.rafaelduransaez.feature.myjastic.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.zero
import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.core.extensions.getAndRemove
import com.rafaelduransaez.core.navigation.KEY_DATA
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs.MyJasticGraph
import com.rafaelduransaez.core.permissions.OnPermissionNeeded
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailScreen
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailUserEvent.LocationSelected
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailViewModel
import com.rafaelduransaez.feature.myjastic.presentation.map.MapScreen
import com.rafaelduransaez.feature.myjastic.presentation.map.MapViewModel
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticScreen
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticViewModel
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_ADDRESS
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_LATITUDE
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_LONGITUDE
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_RADIUS
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes.JasticDestinationDetail
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes.Map
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

        composable<JasticDestinationDetail> { currentBackStackEntry ->
            val viewModel: JasticDestinationDetailViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            with(currentBackStackEntry.savedStateHandle) {
                LaunchedEffect(this) {
                    if (keys().isNotEmpty()) {
                        /*                      val data = getAndRemove<MapNavData>(KEY_DATA, MapNavData.Empty)
                                                when (data) {
                                                    is MapNavData.Location ->
                                                        viewModel.onUiEvent(LocationSelected(data.toGeofenceLocation()))
                                                    MapNavData.Empty -> Unit
                                                }*/
                        val location = this@with.toGeofenceLocation()
                        viewModel.onUiEvent(LocationSelected(location))
                    }
                }
            }

            JasticDestinationDetailScreen(
                uiState = uiState,
                onUiEvent = viewModel::onUiEvent,
                onRouteTo = onRouteTo,
                onPermissionNeeded = onPermissionNeeded
            )
        }

        composable<Map> {
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

object Keys {
    const val KEY_LONGITUDE = "longitude"
    const val KEY_LATITUDE = "latitude"
    const val KEY_ADDRESS = "address"
    const val KEY_RADIUS = "radius"
}
