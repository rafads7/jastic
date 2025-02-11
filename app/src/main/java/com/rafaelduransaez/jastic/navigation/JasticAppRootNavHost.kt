package com.rafaelduransaez.jastic.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.NavigationRoutes
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticNavActions
import com.rafaelduransaez.feature.myjastic.presentation.navigation.myJasticNavGraph
import com.rafaelduransaez.feature.myjastic.presentation.navigation.navigateToJasticDestinationDetail
import com.rafaelduransaez.feature.myjastic.presentation.navigation.navigateToMap
import com.rafaelduransaez.feature.settings.navigation.settingsGraph

@Composable
fun JasticAppRootNavGraph(
    contentPadding: PaddingValues,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState
    /*navigator: JasticNavigator*/
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = contentPadding)
            .background(JasticTheme.colorScheme.onPrimary)
    ) {
        NavHost(navController = navController, startDestination = NavigationGraphs.MyJasticGraph) {
            myJasticNavGraph { action -> navController.navigateTo(action) }
            settingsGraph()
        }
    }

}

private fun NavHostController.navigateTo(action: MyJasticNavActions) {
    when (action) {
        MyJasticNavActions.Back -> popBackStack()
        MyJasticNavActions.Map -> navigateToMap()
        is MyJasticNavActions.JasticDestinationDetail -> navigateToJasticDestinationDetail(action.id)
    }
}