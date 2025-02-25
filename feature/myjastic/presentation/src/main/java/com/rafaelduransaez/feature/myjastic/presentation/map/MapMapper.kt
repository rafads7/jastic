package com.rafaelduransaez.feature.myjastic.presentation.map

import com.google.android.gms.maps.model.LatLng
import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.feature.myjastic.presentation.utils.toGeofenceLocation
import javax.inject.Singleton

interface MapMapper {
    fun latLngToGeofenceLocation(latLng: LatLng): GeofenceLocation
}

@Singleton
class MapMapperImpl : MapMapper {
    override fun latLngToGeofenceLocation(latLng: LatLng) = latLng.toGeofenceLocation()
}