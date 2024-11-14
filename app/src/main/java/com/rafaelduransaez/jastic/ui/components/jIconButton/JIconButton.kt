package com.rafaelduransaez.jastic.ui.components.jIconButton

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.rafaelduransaez.jastic.ui.components.jIcon.JIcon

@Composable
fun JIconButton(model: JIconButtonModel) =
    IconButton(onClick = model.onClick) {
        JIcon(
            icon = model.icon,
            contentDescription = model.iconContentDescription
        )
    }





