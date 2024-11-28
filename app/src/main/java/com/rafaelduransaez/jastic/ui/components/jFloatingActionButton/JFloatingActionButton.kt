package com.rafaelduransaez.jastic.ui.components.jFloatingActionButton

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.rafaelduransaez.jastic.ui.components.jIcon.JIcon
import kotlinx.coroutines.launch

@Composable
fun JFloatingActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit

) {

    val scope = rememberCoroutineScope()
    FloatingActionButton(
        onClick = { onClick() },
        shape = ShapeDefaults.ExtraLarge,
        modifier = modifier
    ) {
        JIcon(icon = icon)
    }

}
