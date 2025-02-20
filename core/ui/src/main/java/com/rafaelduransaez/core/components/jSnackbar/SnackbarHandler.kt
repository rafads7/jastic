package com.rafaelduransaez.core.components.jSnackbar

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SnackbarHandler {
    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.send(event)
    }
}

data class SnackbarEvent(
    val message: String,
    val actionLabel: String? = null,
    val action: () -> Unit = {},
    val duration: SnackbarDuration = SnackbarDuration.Short
)