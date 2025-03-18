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
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.navigateTo
import com.rafaelduransaez.core.ui.permissions.PermissionsRequestHolder
import com.rafaelduransaez.core.ui.permissions.PermissionsRequestHolder.Companion.fromJasticPermission
import com.rafaelduransaez.feature.myjastic.presentation.navigation.myJasticNavGraph
import com.rafaelduransaez.feature.settings.navigation.settingsGraph

@Composable
fun JasticAppRootNavGraph(
    contentPadding: PaddingValues,
    navController: NavHostController,
    onPermissionsRequest: (PermissionsRequestHolder) -> Unit
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
            myJasticNavGraph(
                onRouteTo = { route, navData, options ->
                    navController.navigateTo(route, navData, options)
                },
                onPermissionNeeded = { permission, onAllGranted ->
                    onPermissionsRequest(PermissionsRequestHolder.fromJasticPermission(permission, onAllGranted))
                }
            )
            settingsGraph()
        }
    }

}
