package com.rafaelduransaez.feature.map.di

import com.rafaelduransaez.core.domain.sources.AddressHelper
import com.rafaelduransaez.core.domain.sources.LocationHelper
import com.rafaelduransaez.feature.map.data.LocationRepositoryImpl
import com.rafaelduransaez.feature.map.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideLocationRepository(
        locationHelper: LocationHelper,
        addressHelper: AddressHelper
    ): LocationRepository {
        return LocationRepositoryImpl(
            locationHelper,
            addressHelper
        )
    }

}