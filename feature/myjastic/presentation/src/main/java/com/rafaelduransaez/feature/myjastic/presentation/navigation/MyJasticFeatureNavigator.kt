package com.rafaelduransaez.feature.myjastic.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.FeatureNavAction
import com.rafaelduransaez.core.navigation.FeatureNavigator
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.NavigationRoutes
import com.rafaelduransaez.core.utils.extensions.negative
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailScreen
import com.rafaelduransaez.feature.myjastic.presentation.map.MapScreen
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticSection

class MyJasticFeatureNavigator : FeatureNavigator {

    override fun navigateTo(action: FeatureNavAction) {
/*        if (action is MyJasticNavActions) {
            when (action) {
                MyJasticNavActions.Back -> popBackStack()
                MyJasticNavActions.Map -> navigateToMap()
                is MyJasticNavActions.JasticDestinationDetail ->
                    navigateToJasticDestinationDetail(action.id)
            }
        }*/
    }
}

sealed class MyJasticNavActions : FeatureNavAction {
    data object Back : MyJasticNavActions()
    data object Map : MyJasticNavActions()
    data class JasticDestinationDetail(val id: Int = Int.negative()) : MyJasticNavActions()
}

fun NavController.navigateToMap() {
    navigate(route = NavigationRoutes.Map)
}

fun NavController.navigateToJasticDestinationDetail(jasticDestinationId: Int = Int.negative()) {
    navigate(route = NavigationRoutes.JasticDestinationDetail(jasticDestinationId))
}

typealias OnNavigateTo = (FeatureNavAction, NavGraphBuilder.() -> Unit) -> Unit

fun NavGraphBuilder.myJasticNavGraph(
    onAction: (MyJasticNavActions) -> Unit //OnNavigateTo //(MyJasticNavActions) -> Unit
) {
    navigation<NavigationGraphs.MyJasticGraph>(startDestination = NavigationRoutes.MyJastic) {

        composable<NavigationRoutes.MyJastic> {
            MyJasticSection(
                onJasticDestinationSelected = { id ->
                    onAction(MyJasticNavActions.JasticDestinationDetail(id))
                }
            )
        }

        composable<NavigationRoutes.JasticDestinationDetail> {
            JasticDestinationDetailScreen(onOpenGoogleMaps = { onAction(MyJasticNavActions.Map) })
        }

        composable<NavigationRoutes.Map> {
            MapScreen(
                onSave = { onAction(MyJasticNavActions.Back) },
                onCancel = { onAction(MyJasticNavActions.Back) }
            )
        }
    }
}
