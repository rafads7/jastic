package com.rafaelduransaez.jastic.ui.components.jFloatingActionButton

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import com.rafaelduransaez.jastic.ui.components.jIcon.JIcon

@Composable
fun JFloatingActionButton(model: JFloatingActionButtonModel) =
    FloatingActionButton(
        onClick = { model.onClick() },
        shape = ShapeDefaults.ExtraLarge,
        modifier = model.modifier
    ) {
        JIcon(icon = model.icon)
    }