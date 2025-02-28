package com.rafaelduransaez.jastic.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.Back
import com.rafaelduransaez.core.navigation.JasticNavigable
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.permissions.JasticPermission
import com.rafaelduransaez.core.ui.permissions.PermissionsRequester
import com.rafaelduransaez.core.permissions.toAndroidPermissions
import com.rafaelduransaez.feature.myjastic.presentation.navigation.myJasticNavGraph
import com.rafaelduransaez.feature.settings.navigation.settingsGraph
import com.rafaelduransaez.jastic.navigation.PermissionsRequestHolder.Companion.empty
import com.rafaelduransaez.jastic.navigation.PermissionsRequestHolder.Companion.fromJasticPermission
import kotlinx.coroutines.CoroutineScope

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
                    //requestPermissions = PermissionsRequestHolder.fromJasticPermission(permission, onAllGranted)
                    onPermissionsRequest(PermissionsRequestHolder.fromJasticPermission(permission, onAllGranted))
                }
            )
            settingsGraph()
        }
    }

}

data class PermissionsRequestHolder(
    val permissions: List<String>,
    val onAllGranted: () -> Unit
) {
    companion object {

        fun Companion.empty() = PermissionsRequestHolder(
            permissions = emptyList(), onAllGranted = {}
        )

        fun Companion.fromJasticPermission(
            permissionList: JasticPermission,
            onAllGranted: () -> Unit
        ) = fromJasticPermissions(listOf(permissionList), onAllGranted)

        fun Companion.fromJasticPermissions(
            permissionList: List<JasticPermission>,
            onAllGranted: () -> Unit
        ) = PermissionsRequestHolder(
            permissions = permissionList.flatMap { it.toAndroidPermissions() },
            onAllGranted = onAllGranted
        )
    }
}

private fun NavHostController.navigateTo(
    route: JasticNavigable,
    navData: Map<String, Any>,
    options: NavOptionsBuilder.() -> Unit
) {
    navData.forEach {
        previousBackStackEntry?.savedStateHandle?.set(it.key, it.value)
    }

    when (route) {
        Back -> navigateUp()
        else -> navigate(route, navOptions(options))
    }
}

/*
private fun NavHostController.navigateTo(action: MyJasticNavActions) {
    when (action) {
        MyJasticNavActions.Back -> popBackStack()
        MyJasticNavActions.Map -> navigateToMap()
        is MyJasticNavActions.JasticDestinationDetail -> navigateToJasticDestinationDetail(action.id)
    }
}
*/
