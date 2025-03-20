package com.rafaelduransaez.feature.saved_destinations.presentation.navigation

import com.rafaelduransaez.core.navigation.JasticNavigable
import kotlinx.serialization.Serializable

@Serializable
sealed class SavedDestinationsRoutes: JasticNavigable {

    @Serializable
    data object SavedDestinations : SavedDestinationsRoutes()

}