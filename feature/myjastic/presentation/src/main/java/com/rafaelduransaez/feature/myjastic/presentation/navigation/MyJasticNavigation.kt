package com.rafaelduransaez.feature.myjastic.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.Back
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailScreen
import com.rafaelduransaez.feature.myjastic.presentation.map.MapScreen
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticScreen
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticViewModel

fun NavGraphBuilder.myJasticNavGraph(onRouteTo: NavRouteTo) {
    navigation<NavigationGraphs.MyJasticGraph>(startDestination = MyJasticRoutes.MyJastic) {
        composable<MyJasticRoutes.MyJastic> {
            val viewModel = hiltViewModel<MyJasticViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            MyJasticScreen(
                uiState = uiState,
                onJasticDestinationClicked = { id ->
                    onRouteTo(MyJasticRoutes.JasticDestinationDetail(id)) {}
                }
            )
        }

        composable<MyJasticRoutes.JasticDestinationDetail> {
            JasticDestinationDetailScreen(onOpenGoogleMaps = { onRouteTo(MyJasticRoutes.Map) {} })
        }

        composable<MyJasticRoutes.Map> {
            MapScreen(
                onSave = { onRouteTo(Back) {} },
                onCancel = { onRouteTo(Back) {} }
            )
        }
    }
}
