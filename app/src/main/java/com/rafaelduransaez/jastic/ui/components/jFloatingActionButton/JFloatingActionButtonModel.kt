package com.rafaelduransaez.jastic.ui.components.jFloatingActionButton

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class JFloatingActionButtonModel(
    val modifier: Modifier = Modifier,
    val icon: ImageVector,
    val onClick: () -> Unit = {}
)
