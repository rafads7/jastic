package com.rafaelduransaez.feature.settings.presentation.navigation

import com.rafaelduransaez.core.navigation.JasticNavigable
import kotlinx.serialization.Serializable

@Serializable
sealed interface SettingsRoutes : JasticNavigable {
    @Serializable
    data object Settings : SettingsRoutes
}