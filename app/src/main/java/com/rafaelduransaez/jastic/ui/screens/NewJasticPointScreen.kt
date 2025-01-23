package com.rafaelduransaez.jastic.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.components.jButton.JButton
import com.rafaelduransaez.jastic.ui.components.jButton.JSaveButton
import com.rafaelduransaez.jastic.ui.components.jTextField.JOutlinedTextField
import com.rafaelduransaez.jastic.ui.components.jTextField.JOutlinedTextFieldWithIconButton
import com.rafaelduransaez.jastic.ui.theme.JasticTheme

@Preview
@Composable
fun NewDestinyPointScreen(
    onSave: () -> Unit = {},
    onOpenGoogleMaps: () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(all = JasticTheme.size.normal)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        Column(/*modifier = Modifier.fillMaxSize()*/) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Alias()
                Location {
                    onOpenGoogleMaps()
                }

//                TextFieldsDivider()
//                Contact()
//                Contact()
//                Alias()
//                Alias()

            }
            JSaveButton { onSave() }
        }
    }
}

@Preview
@Composable
fun TextFieldWithPlaceholder() {
    var alwaysMinimizeLabel by remember { mutableStateOf(false) }
    val state = rememberTextFieldState()
    snapshotFlow { state.text }
    Column {
        Row {
            Checkbox(checked = alwaysMinimizeLabel, onCheckedChange = { alwaysMinimizeLabel = it })
            Text("Show placeholder even when unfocused")
        }
        Spacer(Modifier.height(16.dp))
        TextField(
            state = state,
            lineLimits = TextFieldLineLimits.SingleLine,
            label = { Text("Email") },
            labelPosition = TextFieldLabelPosition.Attached(alwaysMinimize = alwaysMinimizeLabel),
            placeholder = { Text("example@gmail.com") }
        )
    }
}

@Composable
fun Alias() {
    JOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = JasticTheme.size.extraSmall),
        hint = R.string.str_destiny_point_alias
    )
}

@Composable
fun Location(onIconClick: () -> Unit) {
    JOutlinedTextFieldWithIconButton(
        onIconClick = onIconClick,
        icon = Icons.Default.LocationOn,
        hint = R.string.str_location,
        onValueChange = { }
    )
}