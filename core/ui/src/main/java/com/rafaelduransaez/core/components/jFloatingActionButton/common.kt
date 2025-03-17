package com.rafaelduransaez.core.components.jFloatingActionButton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddFAB(modifier: Modifier = Modifier, onAdd: () -> Unit = {}) =
    JFloatingActionButton(modifier = modifier, icon = Icons.Default.Add, onClick = onAdd)

@Composable
fun LocationFAB(modifier: Modifier = Modifier, onClick: () -> Unit) =
    JFloatingActionButton(modifier = modifier, icon = Icons.Default.LocationOn, onClick = onClick)
