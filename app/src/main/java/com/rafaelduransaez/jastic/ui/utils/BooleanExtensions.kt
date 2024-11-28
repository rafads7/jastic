package com.rafaelduransaez.jastic.ui.utils

import androidx.compose.runtime.MutableState

fun MutableState<Boolean>.toFalse() {
    value = false
}

fun MutableState<Boolean>.toTrue() {
    value = true
}
