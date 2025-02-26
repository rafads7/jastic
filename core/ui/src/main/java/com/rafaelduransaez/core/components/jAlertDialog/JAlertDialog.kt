package com.rafaelduransaez.core.components.jAlertDialog

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.rafaelduransaez.core.components.R
import com.rafaelduransaez.core.components.jIcon.JIcon
import com.rafaelduransaez.core.components.jText.JDialogText
import com.rafaelduransaez.core.components.jText.JText
import com.rafaelduransaez.core.designsystem.JasticTheme

@Preview
@Composable
fun JAlertDialog(
    modifier: Modifier = Modifier,
    title: String = "Title",
    description: String = "Desc",
    onDismissRequest: () -> Unit = { },
    onConfirm: () -> Unit = { },
    onDismiss: (() -> Unit)? = null,
    @StringRes confirmButtonTextId: Int = R.string.str_core_ui_ok,
    @StringRes dismissButtonTextId: Int = R.string.str_core_ui_cancel,
    dismissOnClickOutside: Boolean = false,
    dismissOnBackPress: Boolean = false
) {
    AlertDialog(
        modifier = modifier,
        properties = DialogProperties(
            dismissOnClickOutside = dismissOnClickOutside,
            dismissOnBackPress = dismissOnBackPress
        ),
        iconContentColor = JasticTheme.colorScheme.secondaryContainer,
        icon = {
            JIcon(
                icon = Icons.Filled.Settings,
                tint = JasticTheme.colorScheme.primaryContainer
            )
        },
        title = { JText(text = title) },
        text = { JText(text = description) },
        confirmButton = {
            JDialogText(
                textId = confirmButtonTextId,
                modifier = Modifier.clickable { onConfirm() }
            )
        },
        dismissButton = {
            onDismiss?.let {
                JDialogText(
                    textId = dismissButtonTextId,
                    modifier = Modifier.clickable { onDismiss() }
                )
            }
        },
        onDismissRequest = { onDismissRequest() }
    )
}