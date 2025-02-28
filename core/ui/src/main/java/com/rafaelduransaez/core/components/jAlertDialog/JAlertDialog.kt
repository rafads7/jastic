package com.rafaelduransaez.core.components.jAlertDialog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rafaelduransaez.core.components.R
import com.rafaelduransaez.core.components.jIcon.JIcon
import com.rafaelduransaez.core.components.jText.JDialogTextButton
import com.rafaelduransaez.core.components.jText.JText
import com.rafaelduransaez.core.designsystem.JasticTheme

@Preview
@Composable
fun JAlertDialog(
    modifier: Modifier = Modifier,
    title: String = "Title",
    description: String = "Desc",
    icon: ImageVector = Icons.Filled.Settings,
    onDismissRequest: () -> Unit = { },
    onConfirm: () -> Unit = { },
    onDismiss: (() -> Unit)? = null,
    @StringRes confirmButtonTextId: Int = R.string.str_core_ui_ok,
    @StringRes dismissButtonTextId: Int = R.string.str_core_ui_cancel,
    dismissOnClickOutside: Boolean = false,
    dismissOnBackPress: Boolean = false,
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
                icon = icon,
                tint = JasticTheme.colorScheme.primary
            )
        },
        title = { JText(text = title) },
        text = { JText(text = description) },
        confirmButton = {
            JDialogTextButton(
                textId = confirmButtonTextId,
                onClick = onConfirm
            )
        },
        dismissButton = {
            onDismiss?.let {
                JDialogTextButton(
                    textId = dismissButtonTextId,
                    onClick = onDismiss
                )
            }
        },
        onDismissRequest = { onDismissRequest() }
    )
}

@Composable
fun JCustomDialog(
    modifier: Modifier = Modifier,
    title: String = "Title",
    description: String = "Desc",
    onDismissRequest: () -> Unit = { },
    onConfirm: () -> Unit = { },
    onDismiss: (() -> Unit)? = null,
    @StringRes confirmButtonTextId: Int = R.string.str_core_ui_ok,
    @StringRes dismissButtonTextId: Int = R.string.str_core_ui_cancel,
    dismissOnClickOutside: Boolean = false,
    dismissOnBackPress: Boolean = false,
    headerBackgroundColor: Color = JasticTheme.colorScheme.primary
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnClickOutside = dismissOnClickOutside,
            dismissOnBackPress = dismissOnBackPress
        )
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = JasticTheme.colorScheme.background)
        ) {
            Column {
                // Header with background color
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(headerBackgroundColor)
                        .padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        JIcon(
                            icon = Icons.Filled.Settings,
                            tint = JasticTheme.colorScheme.onPrimary
                        )
                        JText(
                            text = title
                        )
                    }
                }

                // Description
                JText(
                    text = description,
                    modifier = Modifier.padding(16.dp)
                )

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    onDismiss?.let {
                        JDialogTextButton(
                            textId = dismissButtonTextId,
                            onClick = onDismiss
                        )
                    }
                    JDialogTextButton(
                        textId = confirmButtonTextId,
                        onClick = onConfirm
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewJCustomDialog() {
    JCustomDialog(
        title = "Permission Required",
        description = "This feature requires location access.",
        onConfirm = {},
        onDismiss = {},
        headerBackgroundColor = Color.Red
    )
}
