package com.rafaelduransaez.jastic.ui

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.rafaelduransaez.core.components.common.ObserveAsEvent
import com.rafaelduransaez.core.components.jIcon.BackNavigationIcon
import com.rafaelduransaez.core.components.jIcon.JIcon
import com.rafaelduransaez.core.components.jSnackbar.SnackbarHandler
import com.rafaelduransaez.core.components.jText.JTextLarge
import com.rafaelduransaez.core.components.jText.JTextTitle
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.core.navigation.NavigationRoute
import com.rafaelduransaez.core.ui.permissions.PermissionsRequester
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.navigation.JasticAppRootNavGraph
import com.rafaelduransaez.jastic.navigation.PermissionsRequestHolder
import com.rafaelduransaez.jastic.navigation.PermissionsRequestHolder.Companion.empty
import com.rafaelduransaez.jastic.navigation.TopLevelRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun JasticApp(appState: JasticAppState = rememberJasticAppState()) {

    val snackBarHostState = remember { SnackbarHostState() }

    SnackbarHostHandler(snackBarHostState, appState.coroutineScope)

    Scaffold(
        topBar = { JasticTopAppBar() },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        bottomBar = {
            JasticBottomBar(
                currentDestination = appState.currentDestination,
                routes = appState.topLevelDestinations,
                onBottomNavItemClicked = { appState.onTopLevelRouteClicked(it) }
            )
        }
    ) { contentPadding ->

        var requestPermissions by remember { mutableStateOf(PermissionsRequestHolder.empty()) }

        PermissionsRequester(
            permissions = requestPermissions.permissions,
            coroutineScope = appState.coroutineScope,
            onCancelRequest = { requestPermissions = PermissionsRequestHolder.empty() }
        ) {
            requestPermissions.onAllGranted()
        }

        JasticAppRootNavGraph(
            contentPadding = contentPadding,
            navController = appState.navController,
            onPermissionsRequest = { holder -> requestPermissions = holder }
        )
    }
}

@Composable
private fun SnackbarHostHandler(snackBarHostState: SnackbarHostState, coroutineScope: CoroutineScope) {
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
fun JasticTopAppBar(
    showNavIcon: Boolean = false,
    onNavIconClick: () -> Unit = {}
) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = JasticTheme.colorScheme.primaryContainer,
            titleContentColor = JasticTheme.colorScheme.primary,
        ),
        title = { JTextTitle(textId = R.string.app_name) },
        navigationIcon = {
            if (showNavIcon)
                BackNavigationIcon(onNavIconClick)
        }
    )
}

@Composable
fun JasticBottomBar(
    modifier: Modifier = Modifier,
    routes: List<TopLevelRoute<out NavigationGraphs>>,
    onBottomNavItemClicked: (route: NavigationGraphs) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(
        modifier = modifier,
        containerColor = JasticTheme.colorScheme.onPrimary,
        contentColor = JasticTheme.colorScheme.primary
    ) {

        routes.forEach { item ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true

            NavigationBarItem(
                label = { JTextLarge(textId = item.labelId) },
                selected = isSelected,
                onClick = { onBottomNavItemClicked(item.route) },
                icon = {
                    currentDestination?.let {
                        JIcon(
                            icon = if (isSelected) item.icon else item.unselectedIcon,
                            contentDescriptionResId = item.labelId
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = JasticTheme.colorScheme.primary,
                    selectedTextColor = JasticTheme.colorScheme.primary,
                    indicatorColor = JasticTheme.colorScheme.primaryContainer,
                    unselectedIconColor = JasticTheme.colorScheme.primary,
                    unselectedTextColor = JasticTheme.colorScheme.primary
                )
            )
        }
    }
}

inline fun <reified T : NavigationRoute> NavDestination.hasRouteInHierarchy(): Boolean {
    return this.hierarchy.any { it.hasRoute(T::class) }
}