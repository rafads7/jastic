package com.rafaelduransaez.feature.myjastic.presentation.utils

import com.rafaelduransaez.core.contacts.domain.Contact
import com.rafaelduransaez.feature.map.domain.model.GeofenceLocation
import com.rafaelduransaez.feature.myjastic.domain.model.DestinationUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointListItemUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointUI

val mockGeofenceLocation = GeofenceLocation(
    address = "address1",
    latitude = 1.0,
    longitude = 1.0,
    radiusInMeters = 100f
)

val mockDestinationUI1 = DestinationUI(
    id = 1L,
    alias = "alias1",
    address = "address1",
    latitude = 1.0,
    longitude = 1.0,
    radiusInMeters = 100f
)
val mockJasticPointUI1 = JasticPointUI(
    id = 1L,
    alias = "alias1",
    contactAlias = "contactAlias1",
    contactPhone = "contactPhone1",
    message = "message1",
    destinationUI = mockDestinationUI1
)

val mockContact = Contact(
    id = "1",
    name = "name1",
    phoneNumber = "phoneNumber1",
    email = "email1"
)

val mockJasticPointListItemUI1 = JasticPointListItemUI(
    id = 1,
    alias = "alias1",
    address = "address1",
    contactName = "contactName1",
    contactPhone = "contactPhone1",
    message = "message1"
)

val mockJasticPointListItemUI2 = JasticPointListItemUI(
    id = 2,
    alias = "alias2",
    address = "address2",
    contactName = "contactName2",
    contactPhone = "contactPhone2",
    message = "message1"
)

val mockJasticPointsList = listOf(mockJasticPointListItemUI1, mockJasticPointListItemUI2)
