package com.rafaelduransaez.jastic.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rafaelduransaez.jastic.R

data class JasticColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val outlineVariant: Color,
    val scrim: Color,
    val inverseSurface: Color,
    val inverseOnSurface: Color,
    val inversePrimary: Color,
    val surfaceDim: Color,
    val surfaceBright: Color,
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color
)

data class JasticTypography(
    val titleLarge: TextStyle,
    val titleNormal: TextStyle,
    val titleSmall: TextStyle,
    val body: TextStyle,
    val labelLarge: TextStyle,
    val labelBold: TextStyle,
    val labelNormal: TextStyle,
    val labelSmall: TextStyle,
)

data class JasticShape(
    val container: Shape,
    val button: Shape
)

data class JasticSize(
    val extraLarge: Dp,
    val large: Dp,
    val normal: Dp,
    val small: Dp,
    val extraSmall: Dp,
    val minimum: Dp,
    val zero: Dp
)

val LocalJasticColorScheme = staticCompositionLocalOf {
    JasticColorScheme(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        primaryContainer = Color.Unspecified,
        onPrimaryContainer = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        secondaryContainer = Color.Unspecified,
        onSecondaryContainer = Color.Unspecified,
        tertiary = Color.Unspecified,
        onTertiary = Color.Unspecified,
        tertiaryContainer = Color.Unspecified,
        onTertiaryContainer = Color.Unspecified,
        error = Color.Unspecified,
        onError = Color.Unspecified,
        errorContainer = Color.Unspecified,
        onErrorContainer = Color.Unspecified,
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        surfaceVariant = Color.Unspecified,
        onSurfaceVariant = Color.Unspecified,
        outline = Color.Unspecified,
        outlineVariant = Color.Unspecified,
        scrim = Color.Unspecified,
        inverseSurface = Color.Unspecified,
        inverseOnSurface = Color.Unspecified,
        inversePrimary = Color.Unspecified,
        surfaceDim = Color.Unspecified,
        surfaceBright = Color.Unspecified,
        surfaceContainerLowest = Color.Unspecified,
        surfaceContainerLow = Color.Unspecified,
        surfaceContainer = Color.Unspecified,
        surfaceContainerHigh = Color.Unspecified,
        surfaceContainerHighest = Color.Unspecified
    )
}

val LocalJasticTypography = staticCompositionLocalOf {
    JasticTypography(
        titleLarge = TextStyle.Default,
        titleNormal = TextStyle.Default,
        titleSmall = TextStyle.Default,
        body = TextStyle.Default,
        labelLarge = TextStyle.Default,
        labelBold = TextStyle.Default,
        labelNormal = TextStyle.Default,
        labelSmall = TextStyle.Default
    )
}

val LocalJasticShape = staticCompositionLocalOf {
    JasticShape(
        container = RectangleShape,
        button = RectangleShape
    )
}

val LocalJasticSize = staticCompositionLocalOf {
    JasticSize(
        extraLarge = Dp.Unspecified,
        large = Dp.Unspecified,
        normal = Dp.Unspecified,
        small = Dp.Unspecified,
        extraSmall = Dp.Unspecified,
        minimum = Dp.Unspecified,
        zero = 0.dp
    )
}

val LocalJasticFont = FontFamily(
    Font(R.font.jastic_light, weight = FontWeight.Light),
    Font(R.font.jastic_regular, weight = FontWeight.Normal),
    Font(R.font.jastic_semibold, weight = FontWeight.SemiBold),
    Font(R.font.jastic_bold, weight = FontWeight.Bold)
)

