package com.rafaelduransaez.feature.map.data.source

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.rafaelduransaez.core.coroutines.IODispatcher
import com.rafaelduransaez.feature.map.domain.model.GeofenceLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FusedLocationHelper @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val locationRequest: LocationRequest,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
): LocationHelper {

    @SuppressLint("MissingPermission")
    override fun requestLocationUpdates(): Flow<GeofenceLocation?> = callbackFlow {
        try {

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.locations.lastOrNull()?.let {
                        trySend(GeofenceLocation(it.latitude, it.longitude))
                    }
                }
            }

            locationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose {
                locationClient.removeLocationUpdates(locationCallback)
            }
        } catch (e: Exception) {
            close(e)
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun requestCurrentLocation(): Flow<GeofenceLocation?> = flow {
        val location = withContext(ioDispatcher) {
            locationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).await()
        }

        emit(location?.let { GeofenceLocation(it.latitude, it.longitude) })
    }.flowOn(ioDispatcher)

}