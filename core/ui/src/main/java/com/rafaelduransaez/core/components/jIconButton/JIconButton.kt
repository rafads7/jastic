package com.rafaelduransaez.core.components.jIconButton

import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.rafaelduransaez.core.components.jIcon.JIcon
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.domain.extensions.empty

@Composable
fun JIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconContentDescription: String = String.empty,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = JasticTheme.colorScheme.secondaryContainer,
            contentColor = JasticTheme.colorScheme.secondary
        )
    ) {
        JIcon(
            icon = icon,
            contentDescription = iconContentDescription
        )
    }
}





