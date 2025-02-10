package com.rafaelduransaez.core.components.common

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.rafaelduransaez.core.designsystem.JasticTheme

/*
@Composable
fun Toast(@StringRes textId: Int) {
    val context = LocalContext.current

    LaunchedEffect(textId) {
        Toast.makeText(context, context.getString(textId), Toast.LENGTH_LONG).show()
    }
}

@Composable
fun Toast(text: String) {
    val context = LocalContext.current
    LaunchedEffect(text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}*/
@Composable
fun ColumnItemsSpacer(height: Dp = JasticTheme.size.extraSmall) {
    Spacer(Modifier.height(height))
}

@Composable
fun BoxScope.JasticProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.align(Alignment.Center),
        color = JasticTheme.colorScheme.primary
    )
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}