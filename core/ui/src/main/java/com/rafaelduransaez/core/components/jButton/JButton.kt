package com.rafaelduransaez.core.components.jButton

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rafaelduransaez.core.components.R
import com.rafaelduransaez.core.components.jText.JText
import com.rafaelduransaez.core.designsystem.JasticTheme

@Composable
fun JButton(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    enabled: Boolean = true,
    containerColor: Color = JasticTheme.colorScheme.tertiaryContainer,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,//.padding(all = JasticTheme.size.normal),
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
fun JSaveAndGoButton(
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    onSave: () -> Unit
) {
    JButton(
        modifier = modifier.padding(top = JasticTheme.size.extraSmall),
        textId = R.string.str_save_and_go,
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
        textId = R.string.str_core_ui_cancel,
        enabled = enabled,
        containerColor = JasticTheme.colorScheme.errorContainer
    ) { onCancel() }
}