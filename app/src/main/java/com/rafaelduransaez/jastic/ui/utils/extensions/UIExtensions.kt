package com.rafaelduransaez.jastic.ui.utils.extensions

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ComponentActivity.openSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

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
