package com.rafaelduransaez.feature.myjastic.presentation.utils

import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.rafaelduransaez.core.domain.models.GeofenceLocation

fun GeofenceLocation.toLatLng() = LatLng(latitude, longitude)

fun LatLng.toGeofenceLocation() = GeofenceLocation(latitude, longitude)

fun GeofenceLocation?.toMap() = this?.let {
    mapOf(
        "longitude" to longitude,
        "latitude" to latitude,
        "address" to address
    )
} ?: emptyMap()
