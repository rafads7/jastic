package com.rafaelduransaez.core.geofencing.di

import android.content.Context
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.rafaelduransaez.core.geofencing.data.GeofenceHelperImpl
import com.rafaelduransaez.core.geofencing.domain.sources.GeofenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object GeofenceModule {

    @Provides
    @Singleton
    fun provideGeofencingClient(@ApplicationContext context: Context): GeofencingClient {
        return LocationServices.getGeofencingClient(context)
    }

    @Provides
    @Singleton
    fun provideGeofenceHelper(
        @ApplicationContext context: Context,
        geofencingClient: GeofencingClient
    ): GeofenceHelper {
        return GeofenceHelperImpl(context, geofencingClient)
    }
}