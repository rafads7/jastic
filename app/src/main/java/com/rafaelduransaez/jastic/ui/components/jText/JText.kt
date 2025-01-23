package com.rafaelduransaez.jastic.ui.components.jText

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.theme.JasticTheme

@Composable
fun JText(modifier: Modifier = Modifier, @StringRes textId: Int, color: Color = Color.Unspecified) {
    Text(
        text = stringResource(textId),
        modifier = modifier,
        color = color,
        style = JasticTheme.typography.labelNormal
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
fun JTextCardTitle(modifier: Modifier = Modifier, text: String) {
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
fun JTextCardBody(modifier: Modifier = Modifier, text: String) {
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

@Preview
@Composable
fun JDialogText(modifier: Modifier = Modifier, @StringRes textId: Int = R.string.app_name) =
    Text(
        text = stringResource(textId),
        modifier = modifier,
        style = JasticTheme.typography.labelBold,
        color = JasticTheme.colorScheme.primary
    )