package com.rafaelduransaez.jastic.ui.components.jIconButton

import androidx.compose.ui.graphics.vector.ImageVector
import com.rafaelduransaez.domain.components.common.empty

data class JIconButtonModel(
    val icon: ImageVector,
    val iconContentDescription: String = String.empty(),
    val onClick: () -> Unit = { }
)

