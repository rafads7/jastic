package com.rafaelduransaez.jastic.ui.utils

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.launchSnackbar(snackbarHostState: SnackbarHostState, message: String) =
    launch {
        snackbarHostState.showSnackbar(
            message = message,
            withDismissAction = true
        )
    }

fun CoroutineScope.launchSnackbar(
    context: Context,
    snackbarHostState: SnackbarHostState,
    messageId: Int
) = launchSnackbar(snackbarHostState, context.getString(messageId))