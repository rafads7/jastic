package com.rafaelduransaez.jastic.ui.components.jFloatingActionButton

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.testTag
import com.rafaelduransaez.jastic.ui.components.jIcon.JIcon

@Composable
fun JFloatingButton(model: JFloatingActionButtonModel) =

    FloatingActionButton(
        onClick = { model.onClick() },
        shape = ShapeDefaults.ExtraLarge,
        modifier = model.modifier.testTag("jFloatingButtonTag")
    ) {
        JIcon(icon = model.icon)
    }