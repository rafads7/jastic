package com.rafaelduransaez.feature.myjastic.data

import com.rafaelduransaez.core.database.model.DestinationEntity
import com.rafaelduransaez.core.database.model.JasticDestinationPoint
import com.rafaelduransaez.core.database.model.JasticPointEntity
import com.rafaelduransaez.core.domain.extensions.zero
import com.rafaelduransaez.feature.myjastic.domain.model.DestinationUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointListItemUI

fun JasticDestinationPoint.toJasticListItemUI() = JasticPointListItemUI(
    id = point.id,
    alias = point.alias,
    address = destination.address,
    contactName = point.contactName,
    contactPhone = point.contactPhone
)

fun JasticDestinationPoint.toJasticPointUI() = JasticPointUI(
    id = point.id,
    alias = point.alias,
    contactAlias = point.contactName,
    contactPhone = point.contactPhone,
    message = point.message,
    destinationUI = DestinationUI(
        id = destination.id,
        alias = destination.alias,
        address = destination.address,
        latitude = destination.latitude,
        longitude = destination.longitude,
        radiusInMeters = destination.radiusInMeters
    )
)

fun JasticPointUI.toJasticPointEntity() = JasticPointEntity(
    id = id,
    alias = alias,
    contactPhone = contactPhone,
    contactName = contactAlias,
    message = message,
    destinationId = destinationUI.id
)

fun DestinationUI.toDestinationEntity() = DestinationEntity(
    id = id ?: Long.zero,
    alias = alias,
    address = address,
    latitude = latitude,
    longitude = longitude,
    radiusInMeters = radiusInMeters
)