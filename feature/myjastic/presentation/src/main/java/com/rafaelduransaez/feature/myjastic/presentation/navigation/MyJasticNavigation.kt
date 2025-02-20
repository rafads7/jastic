package com.rafaelduransaez.feature.myjastic.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.android.gms.maps.model.LatLng
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailScreen
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailUserEvent.LocationSelected
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailViewModel
import com.rafaelduransaez.feature.myjastic.presentation.map.MapScreen
import com.rafaelduransaez.feature.myjastic.presentation.map.MapViewModel
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticScreen
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticViewModel
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.MAP_LATLNG
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes.JasticDestinationDetail
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes.Map

fun NavGraphBuilder.myJasticNavGraph(
    onRouteTo: NavRouteTo,
) {
    navigation<NavigationGraphs.MyJasticGraph>(startDestination = MyJasticRoutes.MyJastic) {
        composable<MyJasticRoutes.MyJastic> {
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
            val mapLocation = currentBackStackEntry.savedStateHandle.get<LatLng>(MAP_LATLNG)

            LaunchedEffect(mapLocation) {
                mapLocation?.let {
                    viewModel.onUiEvent(LocationSelected(it))
                    currentBackStackEntry.savedStateHandle.remove<LatLng>(MAP_LATLNG)
                }
            }

            JasticDestinationDetailScreen(
                uiState = uiState,
                onUiEvent = viewModel::onUiEvent,
                onRouteTo = onRouteTo
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
    const val MAP_LATLNG = "jastic_destination_latlng"
}
