package com.rafaelduransaez.feature.myjastic.presentation.utils

import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_ADDRESS
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_LATITUDE
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_LONGITUDE
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_RADIUS
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MapNavData

fun GeofenceLocation.toLatLng() = LatLng(latitude, longitude)

fun LatLng.toGeofenceLocation() = GeofenceLocation(latitude, longitude)

fun GeofenceLocation?.toMap() = this?.let {
    mapOf(
        KEY_LONGITUDE to longitude,
        KEY_LATITUDE to latitude,
        KEY_ADDRESS to address,
        KEY_RADIUS to radiusInMeters
    )
} ?: emptyMap()

fun GeofenceLocation.toMapNavLocationData() = MapNavData.Location(
    longitude = longitude,
    latitude = latitude,
    address = address,
    radiusInMeters = radiusInMeters
)
