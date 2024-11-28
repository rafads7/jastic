package com.rafaelduransaez.jastic.ui.components.jIconButton

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.rafaelduransaez.domain.components.common.empty
import com.rafaelduransaez.jastic.ui.components.jIcon.JIcon

@Composable
fun JIconButton(model: JIconButtonModel) =
    IconButton(onClick = model.onClick) {
        JIcon(
            icon = model.icon,
            contentDescription = model.iconContentDescription
        )
    }

@Composable
fun JIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconContentDescription: String = String.empty(),
    onClick: () -> Unit = { }
) =
    IconButton(modifier = modifier, onClick = onClick) {
        JIcon(
            icon = icon,
            contentDescription = iconContentDescription
        )
    }





