package com.rafaelduransaez.jastic.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import com.rafaelduransaez.core.components.common.ObserveAsEvent
import com.rafaelduransaez.core.components.jIcon.BackNavigationIcon
import com.rafaelduransaez.core.components.jIcon.JIcon
import com.rafaelduransaez.core.components.jSnackbar.SnackbarHandler
import com.rafaelduransaez.core.components.jText.JTextLarge
import com.rafaelduransaez.core.components.jText.JTextTitle
import com.rafaelduransaez.core.components.jToolbar.JToolbarConfig
import com.rafaelduransaez.core.components.jToolbar.LocalToolbarController
import com.rafaelduransaez.core.designsystem.JasticTheme.colorScheme
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.permissions.OnPermissionNeeded
import com.rafaelduransaez.core.permissions.PermissionsRequestHolder
import com.rafaelduransaez.core.permissions.PermissionsRequestHolder.Companion.empty
import com.rafaelduransaez.core.permissions.PermissionsRequestHolder.Companion.fromJasticPermission
import com.rafaelduransaez.core.permissions.PermissionsRequester
import com.rafaelduransaez.feature.map.presentation.navigation.mapNavGraph
import com.rafaelduransaez.feature.myjastic.presentation.navigation.myJasticNavGraph
import com.rafaelduransaez.feature.saved_destinations.presentation.navigation.savedDestinationsGraph
import com.rafaelduransaez.feature.settings.presentation.navigation.settingsGraph
import com.rafaelduransaez.jastic.navigation.TopLevelRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun JasticApp(appState: JasticAppState = rememberJasticAppState()) {

    val snackBarHostState = remember { SnackbarHostState() }
    var requestPermissions by remember { mutableStateOf(PermissionsRequestHolder.empty()) }

    LaunchedEffect(appState.currentDestination) {
        appState.toolbarController.resetToolbarWhenNeeded()
    }

    PermissionsRequester(
        permissions = requestPermissions.permissions,
        coroutineScope = appState.coroutineScope,
        onCancelRequest = { requestPermissions = PermissionsRequestHolder.empty() }
    ) {
        requestPermissions.onAllGranted()
    }

    SnackbarHostHandler(snackBarHostState, appState.coroutineScope)

    CompositionLocalProvider(LocalToolbarController provides appState.toolbarController) {
        Scaffold(
            topBar = { JasticTopAppBar(appState.toolbarConfig) },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            bottomBar = {
                AnimatedVisibility(appState.bottomBarIsVisible) {
                    JasticBottomBar(
                        currentDestination = appState.currentDestination,
                        routes = appState.topLevelDestinations,
                        onBottomNavItemClicked = { appState.onTopLevelRouteClicked(it) }
                    )
                }
            }
        ) { contentPadding ->
            JasticNavHost(
                appState = appState,
                contentPadding = contentPadding,
                onRouteTo = { route, navData, options ->
                    appState.navigateTo(route, navData, options)
                },
                onPermissionsNeeded = { permission, onAllGranted ->
                    requestPermissions = PermissionsRequestHolder.fromJasticPermission(
                        permission, onAllGranted
                    )
                }
            )
        }
    }

}


@Composable
fun JasticNavHost(
    appState: JasticAppState,
    contentPadding: PaddingValues,
    onRouteTo: NavRouteTo,
    onPermissionsNeeded: OnPermissionNeeded
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = contentPadding)
            .background(colorScheme.onPrimary)
    ) {
        NavHost(
            navController = appState.navController,
            startDestination = NavigationGraphs.MyJasticGraph
        ) {

            myJasticNavGraph(onRouteTo, onPermissionsNeeded)
            savedDestinationsGraph(onRouteTo, onPermissionsNeeded)
            settingsGraph()
            mapNavGraph(onRouteTo)
        }
    }
}

@Composable
private fun SnackbarHostHandler(
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    ObserveAsEvent(flow = SnackbarHandler.events, snackBarHostState) { event ->
        coroutineScope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()

            val result = snackBarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.actionLabel,
                duration = event.duration,
                withDismissAction = true
            )
            if (result == SnackbarResult.ActionPerformed) {
                event.action()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JasticTopAppBar(toolbarConfig: JToolbarConfig) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = colorScheme.primaryContainer,
            titleContentColor = colorScheme.primary,
        ),
        title = { JTextTitle(textId = toolbarConfig.titleResId) },
        navigationIcon = {
            if (toolbarConfig.navIcon != null)
                BackNavigationIcon(toolbarConfig.onNavIconClicked)
        }
    )
}

@Composable
fun JasticBottomBar(
    modifier: Modifier = Modifier,
    routes: List<TopLevelRoute<out NavigationGraphs>>,
    onBottomNavItemClicked: (route: NavigationGraphs) -> Unit,
    currentDestination: NavDestination?,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = colorScheme.onPrimary,
        contentColor = colorScheme.primary
    ) {

        routes.forEach { item ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.hasRoute(item.navRoute::class) } == true

            NavigationBarItem(
                label = { JTextLarge(textId = item.labelId) },
                selected = isSelected,
                onClick = { onBottomNavItemClicked(item.navRoute) },
                icon = {
                    currentDestination?.let {
                        JIcon(
                            icon = if (isSelected) item.icon else item.unselectedIcon,
                            contentDescriptionResId = item.labelId
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorScheme.primary,
                    selectedTextColor = colorScheme.primary,
                    indicatorColor = colorScheme.primaryContainer,
                    unselectedIconColor = colorScheme.primary,
                    unselectedTextColor = colorScheme.primary
                )
            )
        }
    }
}
