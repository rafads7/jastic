package com.rafaelduransaez.core.extensions

import androidx.compose.runtime.MutableState

fun MutableState<Boolean>.toFalse() {
    value = false
}

fun MutableState<Boolean>.toTrue() {
    value = true
}
