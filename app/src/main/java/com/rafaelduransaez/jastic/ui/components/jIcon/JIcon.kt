package com.rafaelduransaez.jastic.ui.components.jIcon

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.rafaelduransaez.domain.components.common.empty

@Composable
fun JIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String = String.empty(),
    tint: Color = LocalContentColor.current
) =
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
