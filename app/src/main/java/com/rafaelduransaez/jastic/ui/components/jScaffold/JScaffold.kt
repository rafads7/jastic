package com.rafaelduransaez.jastic.ui.components.jScaffold

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.JFloatingActionButton
import com.rafaelduransaez.jastic.ui.components.jTopAppBar.JTopAppBar

@Composable
fun JScaffold(model: JScaffoldModel) =
    Scaffold(
        topBar = { model.topAppBarModel?.let { JTopAppBar(it) } },
        floatingActionButton = { model.floatingActionButtonModel?.let { JFloatingActionButton(it) } },
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
