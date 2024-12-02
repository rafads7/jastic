package com.rafaelduransaez.jastic.ui.screens

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.AddFAB
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.LikeFAB
import com.rafaelduransaez.jastic.ui.components.jIcon.JIcon
import com.rafaelduransaez.jastic.ui.components.jIconButton.JIconButton
import com.rafaelduransaez.jastic.ui.components.jText.JText
import com.rafaelduransaez.jastic.ui.navigation.JasticPointsList
import com.rafaelduransaez.jastic.ui.navigation.NewJasticPoint
import com.rafaelduransaez.jastic.ui.theme.JasticTheme
import com.rafaelduransaez.jastic.ui.utils.permissions.PermissionsRequester
import kotlinx.serialization.ExperimentalSerializationApi

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    var permissionsGranted by rememberSaveable { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val showFAB = showFAB(navController)

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
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = contentPadding)
        ) {
            NavHost(navController = navController, startDestination = JasticPointsList) {
                composable<JasticPointsList> {
                    PermissionsRequester(getInitialPermissions()) {
                        permissionsGranted = true
                    }
                    if (permissionsGranted)
                        JasticPointsList()
                }

                composable<NewJasticPoint> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        //.padding(paddingValues = contentPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        LikeFAB(onLike = {})
                    }
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
    modifier = Modifier.background(Color.Blue),
    title = { JText(textId = R.string.app_name) },
    navigationIcon = {
        JIconButton(onClick = onNavIconClick, icon = Icons.AutoMirrored.Filled.ArrowBack)
    }
)

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun showFAB(navController: NavHostController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route == JasticPointsList.serializer().descriptor.serialName
}

private fun getInitialPermissions() =
    mutableListOf(ACCESS_NOTIFICATION_POLICY, ACCESS_FINE_LOCATION).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) add(POST_NOTIFICATIONS)
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) add(ACCESS_BACKGROUND_LOCATION)
    }
