package com.rafaelduransaez.jastic.ui.components.jScaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rafaelduransaez.jastic.ui.components.jFloatingActionButton.JFloatingActionButtonModel
import com.rafaelduransaez.jastic.ui.components.jTopAppBar.JTopAppBarModel

data class JScaffoldModel(
    val modifier: Modifier = Modifier,
    val topAppBarModel: JTopAppBarModel? = null,
    val floatingActionButtonModel: JFloatingActionButtonModel? = null,
    val snackbarHostState: SnackbarHostState? = null,
    val mainContent: @Composable (PaddingValues) -> Unit = { }
)
