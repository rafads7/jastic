package com.rafaelduransaez.core

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.rafaelduransaez.core.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LocationHelper {
    fun requestLocationUpdates(): Flow<LatLng?>
    suspend fun requestCurrentLocation(): Flow<LatLng?>
}

class LocationHelperImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val locationRequest: LocationRequest,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
): LocationHelper {

    @SuppressLint("MissingPermission")
    override fun requestLocationUpdates(): Flow<LatLng?> = callbackFlow {
        try {

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.locations.lastOrNull()?.let {
                        trySend(LatLng(it.latitude, it.longitude))
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
    override suspend fun requestCurrentLocation(): Flow<LatLng?> = flow {
        val location = withContext(ioDispatcher) {
            locationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).await()
        }

        emit(location?.let { LatLng(it.latitude, it.longitude) })
    }.flowOn(ioDispatcher)

}