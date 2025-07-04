package com.rafaelduransaez.core.components.jTextField

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.rafaelduransaez.core.components.jIconButton.JIconButton
import com.rafaelduransaez.core.components.jText.JText
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.domain.extensions.empty

@Composable
fun JOutlinedTextField(
    modifier: Modifier = Modifier,
    text: String = String.empty,
    onValueChange: (String) -> Unit = { },
    readOnly: Boolean = false,
    @StringRes placeHolder: Int? = null,
    @StringRes hint: Int? = null
) {

    OutlinedTextField(
        modifier = modifier,
        value = text,
        placeholder = { placeHolder?.let { JText(textId = it) }},
        onValueChange = { onValueChange(it) },
        label = { hint?.let { JText(textId = it) } },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = JasticTheme.colorScheme.scrim,
            unfocusedTextColor = JasticTheme.colorScheme.scrim,
            focusedContainerColor = JasticTheme.colorScheme.onPrimary,
            unfocusedContainerColor = JasticTheme.colorScheme.onPrimary,
            focusedBorderColor = JasticTheme.colorScheme.primaryContainer,
            unfocusedBorderColor = JasticTheme.colorScheme.primary,
            cursorColor = JasticTheme.colorScheme.primary,
            focusedPlaceholderColor = JasticTheme.colorScheme.primary,
            unfocusedPlaceholderColor = JasticTheme.colorScheme.primary,
            focusedLabelColor = JasticTheme.colorScheme.primary,
            unfocusedLabelColor = JasticTheme.colorScheme.primary,
            disabledBorderColor = JasticTheme.colorScheme.primary,
            disabledPlaceholderColor = JasticTheme.colorScheme.primary,
            disabledTextColor = JasticTheme.colorScheme.scrim,
        ),
        textStyle = JasticTheme.typography.body,
        readOnly = readOnly,
        enabled = !readOnly
    )
}

@Composable
fun JOutlinedTextFieldWithIconButton(
    modifier: Modifier = Modifier,
    text: String = String.empty,
    onValueChange: (String) -> Unit = {},
    @StringRes hint: Int? = null,
    @StringRes placeHolder: Int? = null,
    icon: ImageVector,
    readOnly: Boolean = false,
    onIconClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(vertical = JasticTheme.size.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JOutlinedTextField(
            modifier = Modifier.weight(1f),
            text = text,
            hint = hint,
            placeHolder = placeHolder,
            readOnly = readOnly,
            onValueChange = {
                onValueChange(it)
            }
        )

        JIconButton(
            modifier = Modifier.padding(start = JasticTheme.size.small),
            icon = icon,
            onClick = onIconClick
        )
    }
}