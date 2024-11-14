package com.rafaelduransaez.jastic.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.JFloatingActionButtonModel
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.JFloatingButton
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.addCheckPointFloatingButtonModel
import com.rafaelduransaez.jastic.ui.components.jIconButton.JIconButtonModel
import com.rafaelduransaez.jastic.ui.components.jScaffold.JScaffold
import com.rafaelduransaez.jastic.ui.components.jScaffold.JScaffoldModel
import com.rafaelduransaez.jastic.ui.components.jTopAppBar.JTopAppBar
import com.rafaelduransaez.jastic.ui.components.jTopAppBar.JTopAppBarModel
import com.rafaelduransaez.jastic.ui.utils.launchSnackbar

@Composable
fun MainScreen() {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    JScaffold(
        JScaffoldModel(
            topAppBarModel = mainTopAppBarModel(context, onBack = {
                coroutineScope.launchSnackbar(snackBarHostState, "Go Back")
            }),
            snackbarHostState = snackBarHostState,
            floatingActionButtonModel = addCheckPointFloatingButtonModel {
                coroutineScope.launchSnackbar(snackBarHostState, "Added clicked")
            },
            mainContent = checkPointList()
        )
    )
}

@Composable
fun checkPointList(): @Composable (PaddingValues) -> Unit = {

}

fun mainTopAppBarModel(context: Context, onBack: () -> Unit = {}) =
    JTopAppBarModel(
        title = context.getString(R.string.app_name),
        navigationIcon = JIconButtonModel(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = onBack
        )
    )



