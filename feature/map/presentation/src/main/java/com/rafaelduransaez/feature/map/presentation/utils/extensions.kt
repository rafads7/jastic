package com.rafaelduransaez.feature.map.presentation.utils

import com.google.android.gms.maps.model.LatLng
import com.rafaelduransaez.core.geofencing.domain.JasticGeofence
import com.rafaelduransaez.feature.map.domain.model.GeofenceLocation
import com.rafaelduransaez.core.navigation.NavigationGraphs.MapGraph.NavKeys.KEY_ADDRESS
import com.rafaelduransaez.core.navigation.NavigationGraphs.MapGraph.NavKeys.KEY_LATITUDE
import com.rafaelduransaez.core.navigation.NavigationGraphs.MapGraph.NavKeys.KEY_LONGITUDE
import com.rafaelduransaez.core.navigation.NavigationGraphs.MapGraph.NavKeys.KEY_RADIUS

fun LatLng.toGeofenceLocation() = GeofenceLocation(latitude, longitude)

fun GeofenceLocation.toLatLng() = LatLng(latitude, longitude)

fun GeofenceLocation?.toMap() = this?.let {
    mapOf(
        KEY_LONGITUDE to longitude,
        KEY_LATITUDE to latitude,
        KEY_ADDRESS to address,
        KEY_RADIUS to radiusInMeters
    )
} ?: emptyMap()

fun GeofenceLocation.toGeofence() = JasticGeofence(
    requestKey = latitude.toString() + longitude.toString(),
    latitude = latitude,
    longitude = longitude,
    radiusInMeters = radiusInMeters
)

/*fun MapNavData.Location.toGeofenceLocation() = GeofenceLocation(
    latitude,
    longitude,
    address,
    radiusInMeters
)*/

/*
fun GeofenceLocation.toMapNavLocationData() = MapNavData.Location(
    longitude = longitude,
    latitude = latitude,
    address = address,
    radiusInMeters = radiusInMeters
)
@Parcelize
sealed interface MapNavData : JasticNavData, Parcelable {

    @Parcelize
    @Serializable
    data object Empty : MapNavData, Parcelable

    @Parcelize
    data class Location(
        val latitude: Double = Double.zero,
        val longitude: Double = Double.zero,
        val address: String = String.empty,
        val radiusInMeters: Float = Float.NaN
    ) : MapNavData, Parcelable
}
*/
