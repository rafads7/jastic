package com.rafaelduransaez.feature.myjastic.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.FeatureNavAction
import com.rafaelduransaez.core.navigation.FeatureNavigator
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.NavigationRoute
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

fun NavController.navigateToMap() {
    navigate(route = NavigationRoute.Home.Map)
}

fun NavController.navigateToJasticDestinationDetail(jasticDestinationId: Int = Int.negative()) {
    navigate(route = NavigationRoute.Home.JasticDestinationDetail(jasticDestinationId))
}

fun NavGraphBuilder.myJasticNavGraph(
    //onRouteTo: NavRouteTo
    onAction: (MyJasticNavActions) -> Unit
) {
    navigation<NavigationGraphs.MyJasticGraph>(startDestination = NavigationRoute.Home.MyJastic) {

        composable<NavigationRoute.Home.MyJastic> {
            MyJasticSection(
                //onRouteTo = onRouteTo
                onJasticDestinationClicked = { id ->
                    onAction(MyJasticNavActions.JasticDestinationDetail(id))
                }
            )
        }

        composable<NavigationRoute.Home.JasticDestinationDetail> {
            JasticDestinationDetailScreen(onOpenGoogleMaps = { onAction(MyJasticNavActions.Map) })
        }

        composable<NavigationRoute.Home.Map> {
            MapScreen(
                onSave = { onAction(MyJasticNavActions.Back) },
                onCancel = { onAction(MyJasticNavActions.Back) }
            )
        }
    }
}
