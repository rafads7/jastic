package com.rafaelduransaez.core.components.jText

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.rafaelduransaez.core.components.jIcon.JIcon
import com.rafaelduransaez.core.designsystem.JasticTheme

@Composable
fun JText(
    modifier: Modifier = Modifier, text: String, color: Color = Color.Unspecified,
    style: TextStyle = JasticTheme.typography.labelNormal,
) =
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = style
    )

@Composable
fun JText(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    color: Color = Color.Unspecified,
    style: TextStyle = JasticTheme.typography.labelNormal
) {
    JText(
        text = stringResource(textId),
        modifier = modifier,
        color = color,
        style = style
    )
}

@Composable
fun JTextTitle(modifier: Modifier = Modifier, @StringRes textId: Int) {
    Text(
        text = stringResource(textId),
        modifier = modifier,
        style = JasticTheme.typography.titleSemiBold,
        color = JasticTheme.colorScheme.primary
    )
}

@Composable
fun JTextLarge(modifier: Modifier = Modifier, @StringRes textId: Int) {
    Text(
        text = stringResource(textId),
        modifier = modifier,
        style = JasticTheme.typography.bodySemiBold,
    )
}

@Composable
fun JTextButton(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    icon: ImageVector? = null,
    onClick: () -> Unit
) =

    TextButton(modifier = modifier, onClick = onClick) {
        Text(
            text = stringResource(textId),
            style = JasticTheme.typography.labelBold,
            color = JasticTheme.colorScheme.primary
        )
        icon?.let {
            Spacer(modifier = Modifier.width(8.dp))
            JIcon(icon = icon)
        }
    }

@Composable
fun JDialogTextButton(modifier: Modifier = Modifier, @StringRes textId: Int, onClick: () -> Unit) =

    TextButton(modifier = modifier, onClick = onClick) {
        Text(
            text = stringResource(textId),
            style = JasticTheme.typography.labelBold,
            color = JasticTheme.colorScheme.primary
        )
    }
