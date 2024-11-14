package com.rafaelduransaez.jastic.ui.components.jFloatingActionButton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite

fun addCheckPointFloatingButtonModel(onClick: () -> Unit) =
    JFloatingActionButtonModel(icon = Icons.Default.Add, onClick = onClick)

fun likeFloatingButtonModel(onClick: () -> Unit) =
    JFloatingActionButtonModel(icon = Icons.Default.Favorite, onClick = onClick)