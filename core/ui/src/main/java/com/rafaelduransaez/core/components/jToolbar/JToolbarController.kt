package com.rafaelduransaez.core.components.jToolbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

interface JToolbarController {
    fun setToolbar(config: JToolbarConfig)
    fun resetToolbarWhenNeeded()
}

data class JToolbarConfig(
    @StringRes val titleResId: Int,
    val navIcon: ImageVector? = null,
    val onNavIconClicked: () -> Unit = { },
    val actions: @Composable RowScope.() -> Unit = {}
)

