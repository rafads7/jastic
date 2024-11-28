package com.rafaelduransaez.jastic.ui.components.jTextField

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.rafaelduransaez.jastic.ui.components.jText.JText

@Composable
fun JTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    onValueChange: ((String) -> Unit)? = null,
    hint: Int? = null,
) {

    TextField(
        modifier = modifier,
        value = text.value,
        onValueChange = { newValue ->
            onValueChange?.let {
                it(newValue)
            } ?: run {
                text.value = newValue
            }
        },
        label = { hint?.let { JText(textId = it) } }
    )
}