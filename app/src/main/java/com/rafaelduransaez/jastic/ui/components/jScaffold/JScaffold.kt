package com.rafaelduransaez.jastic.ui.components.jScaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.JFloatingButton
import com.rafaelduransaez.jastic.ui.components.jTopAppBar.JTopAppBar

@Composable
fun JScaffold(model: JScaffoldModel) =
    Scaffold(
        topBar = { model.topAppBarModel?.let { JTopAppBar(it) } },
        floatingActionButton = { model.floatingActionButtonModel?.let { JFloatingButton(it) } },
        snackbarHost = { model.snackbarHostState?.let { SnackbarHost(it) } },
        modifier = model.modifier.fillMaxSize(),
        content = model.mainContent
    )

/*
@Composable
fun MyScaffold(
    topAppBar: MyTopAppBar? = null, // Now accepts MyTopAppBar
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { topAppBar?.invoke() }, // Invoke MyTopAppBar if provided
        floatingActionButton = floatingActionButton,
        content = content
    )
}

// Define MyTopAppBar as an interface
interface MyTopAppBar {
    @Composable
    operator fun invoke()
}

// Create a class that implements MyTopAppBar
class MyTopAppBarImpl(
    val title: String,
    val navigationIcon: @Composable (() -> Unit)? = null,
    val actions: @Composable RowScope.() -> Unit = {}
) : MyTopAppBar {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override operator fun invoke() {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = navigationIcon!!,
            actions = actions
        )
    }
}*/
