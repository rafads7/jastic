package com.rafaelduransaez.feature.myjastic.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.NavigationRoutes
import com.rafaelduransaez.core.utils.extensions.zero
import com.rafaelduransaez.feature.myjastic.presentation.map.MapScreen
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticSection
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailScreen

/*interface MyJasticFeatureNavigator : FeatureNavigator {
    fun NavHostController.navigateToMyJastic()
}

class MyJasticFeatureNavigatorImpl : MyJasticFeatureNavigator {

    override fun NavHostController.navigateToMyJastic() {
        navigate(NavigationGraphs.MyJasticGraph)
    }

    override fun NavGraphBuilder.registerNavGraph(
        onBackClicked: () -> Unit,
        onNavigateTo: (NavigationRoutes) -> Unit
    ) {
        TODO("Not yet implemented")
    }

}*/

fun NavController.navigateToMap() {
    navigate(route = NavigationRoutes.Map)
}

fun NavController.navigateToJasticDestinationDetail(jasticDestinationId: Int = Int.zero()) {
    navigate(route = NavigationRoutes.JasticDestinationDetail(jasticDestinationId))
}

fun NavGraphBuilder.myJasticNavGraph(
    onBackClicked: () -> Unit,
    onJasticDestinationSelected: (Int) -> Unit,
    onOpenMaps: () -> Unit,
    ) {
    navigation<NavigationGraphs.MyJasticGraph>(startDestination = NavigationRoutes.MyJastic) {

        composable<NavigationRoutes.MyJastic> {
            MyJasticSection(onJasticDestinationSelected = { id -> onJasticDestinationSelected(id) })
        }

        composable<NavigationRoutes.JasticDestinationDetail> {
            JasticDestinationDetailScreen(onOpenGoogleMaps = { onOpenMaps() })
        }

        composable<NavigationRoutes.Map> {
            MapScreen(
                onSave = { onBackClicked() },
                onCancel = { onBackClicked() }
            )
        }
    }
}
