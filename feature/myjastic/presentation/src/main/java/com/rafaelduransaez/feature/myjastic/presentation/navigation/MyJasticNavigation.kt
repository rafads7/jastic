package com.rafaelduransaez.feature.myjastic.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.Back
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailScreen
import com.rafaelduransaez.feature.myjastic.presentation.map.MapScreen
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticScreen

fun NavGraphBuilder.myJasticNavGraph(onRouteTo: NavRouteTo) {
    navigation<NavigationGraphs.MyJasticGraph>(startDestination = MyJasticRoutes.MyJastic) {

        composable<MyJasticRoutes.MyJastic> {
            MyJasticScreen(
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
