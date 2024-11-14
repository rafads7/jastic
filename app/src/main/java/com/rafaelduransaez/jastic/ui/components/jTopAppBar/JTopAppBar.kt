package com.rafaelduransaez.jastic.ui.components.jTopAppBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.rafaelduransaez.jastic.ui.components.jIconButton.JIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JTopAppBar(model: JTopAppBarModel) =
    TopAppBar(
        title = { Text(text = model.title) },
        navigationIcon = { model.navigationIcon?.let { JIconButton(it) }},
        actions = { model.actions.forEach { JIconButton(it) } }
    )
