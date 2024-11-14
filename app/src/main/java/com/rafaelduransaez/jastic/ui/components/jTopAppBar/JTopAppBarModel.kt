package com.rafaelduransaez.jastic.ui.components.jTopAppBar

import com.rafaelduransaez.jastic.ui.components.jIconButton.JIconButtonModel

data class JTopAppBarModel(
    val title: String,
    val navigationIcon: JIconButtonModel? = null,
    val onBack: () -> Unit = {},
    val actions: List<JIconButtonModel> = emptyList()
)
