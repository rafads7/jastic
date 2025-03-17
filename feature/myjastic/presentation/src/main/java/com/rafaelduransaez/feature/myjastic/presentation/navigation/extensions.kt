package com.rafaelduransaez.feature.myjastic.presentation.navigation

import com.rafaelduransaez.core.domain.models.GeofenceLocation

fun MapNavData.Location.toGeofenceLocation() = GeofenceLocation(
    latitude,
    longitude,
    address,
    radiusInMeters
)