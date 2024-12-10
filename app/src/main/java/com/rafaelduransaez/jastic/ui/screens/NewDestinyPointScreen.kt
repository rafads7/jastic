package com.rafaelduransaez.jastic.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafaelduransaez.domain.components.common.empty
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.components.jIconButton.JIconButton
import com.rafaelduransaez.jastic.ui.components.jText.JText
import com.rafaelduransaez.jastic.ui.components.jTextField.JTextField

@Preview
@Composable
fun NewDestinyPointScreen(
    onSave: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Alias()
                TextFieldsDivider()
                Contact()
            }
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = onSave
            ) {
                JText(textId = R.string.str_save)
            }
        }

    }
}


@Composable
fun Alias() {
    val alias = rememberSaveable { mutableStateOf(String.empty()) }

    JTextField(
        modifier = Modifier.fillMaxWidth(),
        text = alias,
        hint = R.string.str_destiny_point_alias
    )
}

@Composable
fun Contact() {
    val alias = rememberSaveable { mutableStateOf(String.empty()) }

    Row(modifier = Modifier.fillMaxWidth()) {
        JTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = alias,
            hint = R.string.str_contact
        )
        JIconButton(
            modifier = Modifier.padding(start = 4.dp),
            icon = Icons.Default.AccountBox
        )
    }
    TextFieldsDivider()
    JTextField(
        modifier = Modifier.fillMaxWidth(),
        text = alias,
        hint = R.string.str_contact_name_optional
    )
}

@Composable
fun TextFieldsDivider() = HorizontalDivider(thickness = 4.dp)
