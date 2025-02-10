package com.rafaelduransaez.core.designsystem

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkColorScheme = JasticColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark
)

private val LightColorScheme = JasticColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight
)

private val typography = JasticTypography(
    titleSemiBold = TextStyle(
        fontFamily = LocalJasticFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    title = TextStyle(
        fontFamily = LocalJasticFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    titleLight = TextStyle(
        fontFamily = LocalJasticFont,
        fontWeight = FontWeight.Light,
        fontSize = 24.sp
    ),
    body = TextStyle(
        fontFamily = LocalJasticFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySemiBold = TextStyle(
        fontFamily = LocalJasticFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    labelBold = TextStyle(
        fontFamily = LocalJasticFont,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    labelNormal = TextStyle(
        fontFamily = LocalJasticFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelLight = TextStyle(
        fontFamily = LocalJasticFont,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
)

private val shape = JasticShape(
    container = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(12.dp)
)

private val size = JasticSize(
    extraLarge = 24.dp,
    large = 16.dp,
    normal = 12.dp,
    small = 8.dp,
    extraSmall = 4.dp,
    minimum = 2.dp,
    zero = 0.dp
)

@Composable
fun JasticTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalJasticColorScheme provides colorScheme,
        LocalJasticTypography provides typography,
        LocalJasticShape provides shape,
        LocalJasticSize provides size,
        LocalIndication provides ripple(),
        content = content
    )
}

object JasticTheme {
    val colorScheme: JasticColorScheme
        @Composable
        get() = LocalJasticColorScheme.current

    val typography: JasticTypography
        @Composable
        get() = LocalJasticTypography.current

    val shape: JasticShape
        @Composable
        get() = LocalJasticShape.current

    val size: JasticSize
        @Composable
        get() = LocalJasticSize.current
}