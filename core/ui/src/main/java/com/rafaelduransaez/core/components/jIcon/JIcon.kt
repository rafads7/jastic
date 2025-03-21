package com.rafaelduransaez.core.components.jIcon

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.rafaelduransaez.core.domain.extensions.empty

@Composable
fun JIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String = String.empty,
    tint: Color = LocalContentColor.current
) =
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )

@Composable
fun JIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    @StringRes contentDescriptionResId: Int,
    tint: Color = LocalContentColor.current
) =
    Icon(
        imageVector = icon,
        contentDescription = stringResource(id = contentDescriptionResId),
        modifier = modifier,
        tint = tint
    )
