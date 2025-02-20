package com.rafaelduransaez.feature.myjastic.presentation.utils

import com.google.android.gms.maps.model.LatLng
import com.rafaelduransaez.feature.myjastic.domain.model.GeofenceLocation

fun GeofenceLocation.toLatLng() = LatLng(latitude, longitude)

fun LatLng.toGeofenceLocation() = GeofenceLocation(latitude, longitude)