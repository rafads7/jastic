package com.rafaelduransaez.jastic.ui.components.jFloatingActionButton

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.rafaelduransaez.jastic.ui.components.jIcon.JIcon
import com.rafaelduransaez.jastic.ui.theme.JasticTheme
import kotlinx.coroutines.launch

@Composable
fun JFloatingActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit

) {

    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier,
        containerColor = JasticTheme.colorScheme.primaryContainer,
        contentColor = JasticTheme.colorScheme.secondary
    ) {
        JIcon(icon = icon)
    }

}
