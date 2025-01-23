package com.rafaelduransaez.jastic.ui.screens

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rafaelduransaez.domain.components.common.empty
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.components.common.Toast
import com.rafaelduransaez.jastic.ui.components.common.toast
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.AddFAB
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.LikeFAB
import com.rafaelduransaez.jastic.ui.components.jIcon.JIcon
import com.rafaelduransaez.jastic.ui.components.jIconButton.JIconButton
import com.rafaelduransaez.jastic.ui.components.jText.JText
import com.rafaelduransaez.jastic.ui.components.jText.JTextLarge
import com.rafaelduransaez.jastic.ui.components.jText.JTextTitle
import com.rafaelduransaez.jastic.ui.components.jTextField.JOutlinedTextField
import com.rafaelduransaez.jastic.ui.navigation.Maps
import com.rafaelduransaez.jastic.ui.navigation.MyJastic
import com.rafaelduransaez.jastic.ui.navigation.NewJasticPoint
import com.rafaelduransaez.jastic.ui.navigation.Settings
import com.rafaelduransaez.jastic.ui.navigation.TopLevelRoute
import com.rafaelduransaez.jastic.ui.navigation.bottomNavigationRoutes
import com.rafaelduransaez.jastic.ui.theme.JasticTheme
import com.rafaelduransaez.jastic.ui.utils.permissions.PermissionBox
import com.rafaelduransaez.jastic.ui.utils.permissions.PermissionsRequester

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    var permissionsGranted by rememberSaveable { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val showFAB = showFAB(currentBackStackEntry?.destination)

    Scaffold(
        topBar = {
            MainTopAppBar {
                //TODO: Add navigation
            }
        },
        floatingActionButton = {
            if (showFAB) {
                AddFAB { navController.navigate(route = NewJasticPoint) }
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        bottomBar = {
            JasticBottomBar(
                currentBackStackEntry = currentBackStackEntry,
                onBottomNavItemClicked = {
                    navController.navigate(route = it) {
                        popUpTo(navController.graph.id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = contentPadding)
                .background(JasticTheme.colorScheme.onPrimary)
        ) {
            NavHost(navController = navController, startDestination = MyJastic) {
                composable<MyJastic> {
                    PermissionsRequester(getInitialPermissions()) {
                        permissionsGranted = true
                    }
                    if (permissionsGranted)
                        MyJasticScreen {
                            navController.navigate(route = NewJasticPoint)
                        }
/*                    PermissionBox(modifier = Modifier,
                        permissions = getInitialPermissions(),
                        description = "ASKING PERMISSIONS"
                    ) {
                        MyJasticScreen {
                            navController.navigate(route = NewJasticPoint)
                        }
                    }*/
                }

                composable<NewJasticPoint> {
                    NewDestinyPointScreen(
                        onOpenGoogleMaps = {
                            navController.navigate(route = Maps)
                        }
                    )
                }

                composable<Settings> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LikeFAB(onLike = {})
                    }
                }

                composable<Maps> {
                    MapScreen(
                        onSave = {
                            navController.popBackStack()
                            context.toast("Location saved")
                        },
                        onCancel = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(onNavIconClick: () -> Unit) = TopAppBar(
    colors = topAppBarColors(
        containerColor = JasticTheme.colorScheme.primaryContainer,
        titleContentColor = JasticTheme.colorScheme.primary,
    ),
    title = { JTextTitle(textId = R.string.app_name) }
    //navigationIcon = { BackNavigationIcon(onNavIconClick) }
)

@Composable
fun JasticBottomBar(
    modifier: Modifier = Modifier,
    currentBackStackEntry: NavBackStackEntry?,
    routes: List<TopLevelRoute<out Any>> = bottomNavigationRoutes,
    onBottomNavItemClicked: (route: Any) -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = JasticTheme.colorScheme.onPrimary,
        contentColor = JasticTheme.colorScheme.primary
    ) {
        val currentDestination = currentBackStackEntry?.destination

        routes.forEach { item ->
            NavigationBarItem(
                label = { JTextLarge(textId = item.labelId) },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                onClick = { onBottomNavItemClicked(item.route) },
                icon = {
                    currentDestination?.let {
                        JIcon(icon = item.icon, contentDescriptionResId = item.labelId)
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

@Composable
fun showFAB(navDestination: NavDestination?) =
    navDestination?.hierarchy?.any { it.hasRoute(MyJastic::class) } == true


fun getInitialPermissions() =
    mutableListOf(ACCESS_NOTIFICATION_POLICY, ACCESS_FINE_LOCATION).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) add(POST_NOTIFICATIONS)
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) add(ACCESS_BACKGROUND_LOCATION)
    }

@RequiresApi(Build.VERSION_CODES.Q)
fun getInitialPermissions2() =
    mutableListOf(ACCESS_BACKGROUND_LOCATION)
