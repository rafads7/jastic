package com.rafaelduransaez.jastic.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.permissions.OnPermissionNeeded
import com.rafaelduransaez.feature.map.presentation.navigation.mapNavGraph
import com.rafaelduransaez.feature.myjastic.presentation.navigation.myJasticNavGraph
import com.rafaelduransaez.feature.saved_destinations.presentation.navigation.savedDestinationsGraph
import com.rafaelduransaez.feature.settings.navigation.settingsGraph

@Composable
fun JasticAppRootNavGraph(
    contentPadding: PaddingValues,
    navController: NavHostController,
    onRouteTo: NavRouteTo,
    onPermissionsNeeded: OnPermissionNeeded
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = contentPadding)
            .background(JasticTheme.colorScheme.onPrimary)
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationGraphs.MyJasticGraph
        ) {

            myJasticNavGraph(onRouteTo, onPermissionsNeeded)
            savedDestinationsGraph(onRouteTo, onPermissionsNeeded)
            mapNavGraph(onRouteTo)
            settingsGraph()
        }
    }

}
