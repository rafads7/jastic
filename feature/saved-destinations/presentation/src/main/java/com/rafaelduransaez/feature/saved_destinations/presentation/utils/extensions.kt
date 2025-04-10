package com.rafaelduransaez.feature.saved_destinations.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.feature.saved_destinations.presentation.R
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationUI
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationsListItemUI

@Composable
fun AnnotatedString.Builder.Property(name: String, value: String, end: Boolean = false) {
    with(JasticTheme.typography.bodySemiBold) {
        withStyle(ParagraphStyle(lineHeight = fontSize)) {
            withStyle(SpanStyle(fontWeight = fontWeight)) {
                append("$name: ")
            }
            append(value)
            if (end) append("\n")
        }
    }
}

@Composable
fun DestinationsListItemUI.toAnnotatedString() = buildAnnotatedString {
    Property(
        name = stringResource(R.string.str_feature_saved_destinations_radius),
        value = radiusInMeters.toString(),
        end = true
    )
}