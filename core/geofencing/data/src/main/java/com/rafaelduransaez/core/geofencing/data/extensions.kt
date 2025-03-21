package com.rafaelduransaez.core.geofencing.data

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.rafaelduransaez.core.geofencing.domain.JasticGeofence

private fun Context.checkPermission(permission: String) =
    ActivityCompat.checkSelfPermission(
        this, permission
    ) == PackageManager.PERMISSION_GRANTED


val Context.isLocationPermissionGranted
    get() =
        hasFineLocationPermission && hasBackgroundLocationPermission

val Context.hasFineLocationPermission
    get() = checkPermission(ACCESS_FINE_LOCATION)

val Context.hasBackgroundLocationPermission
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        checkPermission(ACCESS_BACKGROUND_LOCATION)
    } else {
        true
    }

fun JasticGeofence.toGeofence() = Geofence.Builder()
    .setRequestId(requestKey)
    .setCircularRegion(latitude, longitude, radiusInMeters)
    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
    .setExpirationDuration(Geofence.NEVER_EXPIRE)
    .build()

