package com.rafaelduransaez.jastic.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.rafaelduransaez.jastic.ui.geofences.LocationHelper
import com.rafaelduransaez.jastic.ui.geofences.LocationHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    private const val DEFAULT_LOCATION_UPDATES_INTERVAL = 10000L
    private const val DEFAULT_MIN_UPDATE_INTERVAL = 1000L

    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun provideLocationRequest(): LocationRequest {
        return LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            DEFAULT_LOCATION_UPDATES_INTERVAL
        )
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(DEFAULT_MIN_UPDATE_INTERVAL)
            .build()
    }

    @Provides
    @Singleton
    fun provideLocationHelper(
        locationClient: FusedLocationProviderClient,
        locationRequest: LocationRequest,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): LocationHelper {
        return LocationHelperImpl(locationClient, locationRequest, ioDispatcher)
    }
}