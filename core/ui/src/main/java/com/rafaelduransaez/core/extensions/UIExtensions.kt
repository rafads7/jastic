package com.rafaelduransaez.core.extensions

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun SnackbarHostState.show(coroutineScope: CoroutineScope, message: String) =
    coroutineScope.launch {
        showSnackbar(
            message = message,
            withDismissAction = true
        )
    }

fun SnackbarHostState.show(context: Context, coroutineScope: CoroutineScope, messageId: Int) =
    show(coroutineScope, context.getString(messageId))

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
