package com.rafaelduransaez.core.components.jCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.rafaelduransaez.core.components.jIconButton.JIconButton
import com.rafaelduransaez.core.designsystem.JasticTheme

@Composable
fun JCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .padding(vertical = JasticTheme.size.small)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.small,
        onClick = { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = JasticTheme.colorScheme.onSecondary,
            contentColor = JasticTheme.colorScheme.secondary
        ),
        content = content
    )
}

@Composable
fun JCardHeader(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        modifier = modifier
            .background(JasticTheme.colorScheme.secondary)
            .fillMaxWidth()
            .padding(all = JasticTheme.size.small),
        style = JasticTheme.typography.title,
        color = JasticTheme.colorScheme.onPrimary
    )
}

@Composable
fun JCardBody(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        modifier = modifier
            .background(JasticTheme.colorScheme.onPrimary)
            .fillMaxWidth()
            .padding(all = JasticTheme.size.small),
        style = JasticTheme.typography.labelBold,
        color = JasticTheme.colorScheme.secondary
    )
}

@Composable
fun JCardBody(modifier: Modifier = Modifier, text: AnnotatedString) {
    Text(
        text = text,
        modifier = modifier
            .background(JasticTheme.colorScheme.onPrimary)
            .fillMaxWidth()
            .padding(all = JasticTheme.size.small),
        style = JasticTheme.typography.labelBold,
        color = JasticTheme.colorScheme.secondary
    )
}

@Composable
fun JCardIconAction(modifier: Modifier = Modifier, icon: ImageVector, onClick: () -> Unit) {
    JIconButton(
        modifier = modifier,
        icon = icon,
        onClick = onClick
    )
}