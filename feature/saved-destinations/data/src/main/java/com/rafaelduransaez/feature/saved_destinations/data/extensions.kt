package com.rafaelduransaez.feature.saved_destinations.data

import com.rafaelduransaez.core.database.model.DestinationEntity
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationUI
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationsListItemUI

fun DestinationEntity.toDestinationsListItemUI() = DestinationsListItemUI(
    id = id,
    alias = alias,
    address = address,
    radiusInMeters = radiusInMeters
)

fun DestinationEntity.toDestinationUI() = DestinationUI(
    id = id,
    alias = alias,
    address = address,
    radiusInMeters = radiusInMeters,
    longitude = longitude,
    latitude = latitude
)