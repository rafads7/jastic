package com.rafaelduransaez.core.components.jToolbar

import androidx.compose.runtime.staticCompositionLocalOf

val LocalToolbarController = staticCompositionLocalOf<JToolbarController> {
    error("No JToolbarHelper provided")
}
