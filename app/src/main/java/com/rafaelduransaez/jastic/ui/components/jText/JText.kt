package com.rafaelduransaez.jastic.ui.components.jText

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.theme.JasticTheme

@Composable
fun JText(modifier: Modifier = Modifier, @StringRes textId: Int) =
    Text(
        text = stringResource(textId),
        modifier = modifier
    )

@Composable
fun JText(modifier: Modifier = Modifier, text: String) =
    Text(modifier = modifier, text = text)

@Preview
@Composable
fun JDialogText(modifier: Modifier = Modifier, @StringRes textId: Int = R.string.app_name) =
    Text(
        text = stringResource(textId),
        modifier = modifier,
        style = JasticTheme.typography.labelBold,
        color = JasticTheme.colorScheme.primary
    )