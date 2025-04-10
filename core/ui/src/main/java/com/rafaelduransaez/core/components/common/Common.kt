package com.rafaelduransaez.core.components.common

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddLocationAlt
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.rafaelduransaez.core.components.R
import com.rafaelduransaez.core.components.jButton.JButton
import com.rafaelduransaez.core.components.jIcon.JIcon
import com.rafaelduransaez.core.components.jText.JTextTitle
import com.rafaelduransaez.core.designsystem.JasticTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

@Composable
fun BoxScope.JasticDatabaseError(
    modifier: Modifier = Modifier,
    @StringRes textId: Int = R.string.str_core_ui_database_error,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JIcon(
            icon = Icons.Outlined.SearchOff,
            tint = JasticTheme.colorScheme.secondary,
            contentDescriptionResId = textId
        )
        ColumnItemsSpacer(JasticTheme.size.large)
        JTextTitle(textId = textId)
    }
}

@Composable
fun BoxScope.JasticEmptyScreen(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.AddLocationAlt,
    @StringRes textId: Int,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JIcon(
            icon = icon,
            tint = JasticTheme.colorScheme.secondary,
            contentDescriptionResId = textId
        )
        ColumnItemsSpacer(JasticTheme.size.large)
        JTextTitle(textId = textId)
    }
}


@Composable
fun BoxScope.JasticScrollToTopButton(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    listState: LazyListState,
    @StringRes textId: Int = R.string.str_core_ui_scroll_to_top

) {
    JButton(
        modifier = modifier.align(Alignment.TopCenter),
        textId = textId
    ) {
        coroutineScope.launch {
            listState.animateScrollToItem(0)
        }
    }
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}