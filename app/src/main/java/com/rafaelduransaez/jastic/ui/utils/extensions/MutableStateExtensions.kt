package com.rafaelduransaez.jastic.ui.utils.extensions

import androidx.compose.runtime.MutableState

fun MutableState<Boolean>.toFalse() {
    value = false
}

fun MutableState<Boolean>.toTrue() {
    value = true
}
