package com.rafaelduransaez.jastic.ui.components.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun Toast(@StringRes textId: Int) {
    val context = LocalContext.current
    android.widget.Toast.makeText(context, context.getString(textId), android.widget.Toast.LENGTH_LONG).show()
}