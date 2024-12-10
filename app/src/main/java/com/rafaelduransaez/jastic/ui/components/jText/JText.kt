package com.rafaelduransaez.jastic.ui.components.jText

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.theme.JasticSize
import com.rafaelduransaez.jastic.ui.theme.JasticTheme

@Composable
fun JText(modifier: Modifier = Modifier, @StringRes textId: Int) {
    Text(
        text = stringResource(textId),
        modifier = modifier
    )
}

@Composable
fun JText(modifier: Modifier = Modifier, text: String) =
    Text(modifier = modifier, text = text)

@Composable
fun JTextTitle(modifier: Modifier = Modifier, @StringRes textId: Int) {
    Text(
        text = stringResource(textId),
        modifier = modifier,
        style = JasticTheme.typography.titleLarge,
        color = JasticTheme.colorScheme.primary
    )
}

@Composable
fun JTextLarge(modifier: Modifier = Modifier, @StringRes textId: Int) {
    Text(
        text = stringResource(textId),
        modifier = modifier,
        style = JasticTheme.typography.labelLarge
    )
}

@Preview
@Composable
fun JDialogText(modifier: Modifier = Modifier, @StringRes textId: Int = R.string.app_name) =
    Text(
        text = stringResource(textId),
        modifier = modifier,
        style = JasticTheme.typography.labelBold,
        color = JasticTheme.colorScheme.primary
    )