package com.rafaelduransaez.jastic.ui.components.jButton

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.components.jText.JText
import com.rafaelduransaez.jastic.ui.theme.JasticTheme

@Composable
fun JButton(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    enabled: Boolean = true,
    containerColor: Color = JasticTheme.colorScheme.tertiaryContainer,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.padding(all = JasticTheme.size.normal),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        enabled = enabled,
        onClick = { onClick() }
    ) {
        JText(
            textId = textId,
            color = JasticTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun JTextFieldActionButton(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    onClick: () -> Unit
) {
    FilledTonalButton(
        modifier = modifier.padding(
            top = JasticTheme.size.normal,
            bottom = JasticTheme.size.normal,
            start = JasticTheme.size.normal
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = JasticTheme.colorScheme.secondaryContainer
        ),
        onClick = { onClick() }
    ) {
        JText(
            textId = textId,
            color = JasticTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
private fun JActionButtonPreview() {
    JTextFieldActionButton(Modifier, R.string.str_open_map) {}
}

@Composable
fun JSaveButton(
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    onSave: () -> Unit
) {
    JButton(
        modifier = modifier,
        textId = R.string.str_save,
        enabled = enabled
    ) { onSave() }
}


@Composable
fun JCancelButton(
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    onCancel: () -> Unit
) {
    JButton(
        modifier = modifier,
        textId = R.string.str_cancel,
        enabled = enabled,
        containerColor = JasticTheme.colorScheme.errorContainer
    ) { onCancel() }
}