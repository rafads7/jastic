package com.rafaelduransaez.jastic.navigation

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_CONTACTS
import android.os.Build
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
import com.rafaelduransaez.core.permissions.PermissionsRequester
import com.rafaelduransaez.feature.myjastic.presentation.navigation.myJasticNavGraph
import com.rafaelduransaez.feature.settings.navigation.settingsGraph
import kotlinx.coroutines.CoroutineScope

@Composable
fun JasticAppRootNavGraph(
    contentPadding: PaddingValues,
    navController: NavHostController,
    coroutineScope: CoroutineScope
) {
    var permissionsGranted by remember { mutableStateOf(false) }

    val initialPermissions =
        mutableListOf(ACCESS_NOTIFICATION_POLICY, ACCESS_FINE_LOCATION, READ_CONTACTS).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) add(POST_NOTIFICATIONS)
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) add(ACCESS_BACKGROUND_LOCATION)
        }

    PermissionsRequester(initialPermissions) {
        permissionsGranted = true
    }

    if (permissionsGranted)
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
                        navController.navigateTo(
                            route,
                            navData,
                            options
                        )
                    }
                )
                settingsGraph()
            }
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
