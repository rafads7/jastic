package com.rafaelduransaez.feature.myjastic.presentation.utils

import com.rafaelduransaez.feature.map.domain.model.GeofenceLocation
import com.rafaelduransaez.feature.myjastic.domain.model.DestinationUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointUI
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUiState

fun JasticPointDetailUiState.toDestinationUI() = DestinationUI(
        id = destinationId,
        alias = destinationAlias,
        address = geofenceLocation.address,
        latitude = geofenceLocation.latitude,
        longitude = geofenceLocation.longitude,
        radiusInMeters = geofenceLocation.radiusInMeters
    )

fun JasticPointDetailUiState.toJasticPointUI() = JasticPointUI(
        id = jasticPointId,
        alias = jasticPointAlias,
        message = jasticPointMessage,
        contactAlias = contactAlias,
        contactPhone = contactPhoneNumber,
        destinationUI = toDestinationUI()
)

fun JasticPointUI.toJasticPointDetailUiState(currentState: JasticPointDetailUiState) = currentState.copy(
        jasticPointId = id,
        jasticPointAlias = alias,
        jasticPointMessage = message,
        contactAlias = contactAlias,
        contactPhoneNumber = contactPhone,
        destinationId = destinationUI.id,
        destinationAlias = destinationUI.alias,
        geofenceLocation = GeofenceLocation(
                latitude = destinationUI.latitude,
                longitude = destinationUI.longitude,
                address = destinationUI.address,
                radiusInMeters = destinationUI.radiusInMeters
        )
    )