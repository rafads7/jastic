package com.rafaelduransaez.core.components.jFloatingActionButton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rafaelduransaez.core.components.jIcon.JIcon

@Composable
fun AddFAB(onAdd: () -> Unit = {}) =
    JFloatingActionButton(icon = Icons.Default.Add, onClick = onAdd)

@Composable
fun LikeFAB(onLike: () -> Unit, modifier: Modifier = Modifier) =
    JFloatingActionButton(modifier = modifier, icon = Icons.Default.Favorite, onClick = onLike)