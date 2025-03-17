package com.rafaelduransaez.core.geofencing.data

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.rafaelduransaez.core.geofencing.domain.sources.GeofenceHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeofenceHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val client: GeofencingClient
) : GeofenceHelper {
    private val geofenceList = mutableMapOf<String, Geofence>()

    private val geofencingPendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        //val intent = Intent("GEOFENCE_EVENT")
        //intent.setPackage(context.packageName)

        PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                //PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                //PendingIntent.FLAG_UPDATE_CURRENT
                PendingIntent.FLAG_CANCEL_CURRENT
            } else {
                //PendingIntent.FLAG_IMMUTABLE
                PendingIntent.FLAG_MUTABLE
            }
        )
    }

    override fun addGeofence(
        key: String,
        latitude: Double,
        longitude: Double,
        radiusInMeters: Float,
        expirationTimeInMillis: Long,
        onAdded: () -> Unit
    ) {
        if (context.isLocationPermissionGranted.not()) return

        geofenceList[key] = createGeofence(key, latitude, longitude, radiusInMeters)

        registerGeofence { onAdded() }
    }

    @SuppressLint("MissingPermission")
    fun registerGeofence(onAdded: () -> Unit) {
        if (context.isLocationPermissionGranted.not()) return

        client.addGeofences(createGeofencingRequest(), geofencingPendingIntent)
            .addOnSuccessListener {
                Log.d(TAG, "registerGeofence: SUCCESS")
                onAdded()
            }.addOnFailureListener { exception ->
                Log.d(TAG, "registerGeofence: Failure\n$exception")
            }
    }

    override suspend fun unregisterGeofence() = runCatching {
        //client.removeGeofences(geofencingPendingIntent).await()

        client.removeGeofences(geofenceList.keys.toList())
            //client.removeGeofences(geofencingPendingIntent)
            .addOnSuccessListener {
                Log.d(TAG, "unregisterGeofence SUCCESS")
            }.addOnFailureListener { exception ->
                Log.d(TAG, "unregisterGeofence: Failure\n$exception")
            }
            .await()
        geofenceList.clear()
    }

    private fun createGeofencingRequest() = GeofencingRequest.Builder().apply {
        setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        addGeofences(geofenceList.values.toList())
    }.build()

    private fun createGeofence(
        key: String,
        latitude: Double,
        longitude: Double,
        radiusInMeters: Float,
        expirationTimeInMillis: Long = Geofence.NEVER_EXPIRE,
    ): Geofence {
        return Geofence.Builder()
            .setRequestId(key)
            .setCircularRegion(latitude, longitude, radiusInMeters)
            .setTransitionTypes(GEOFENCE_TRANSITION_ENTER or GEOFENCE_TRANSITION_EXIT)
            .setExpirationDuration(expirationTimeInMillis)
            .build()
    }

    companion object {
        private const val TAG = "GeofenceHelper"
        private const val REQUEST_CODE = 1001
    }
}

