package com.rafaelduransaez.feature.myjastic.di

import com.rafaelduransaez.core.di.IODispatcher
import com.rafaelduransaez.core.domain.sources.AddressHelper
import com.rafaelduransaez.core.domain.sources.LocationHelper
import com.rafaelduransaez.feature.myjastic.domain.repository.LocationRepository
import com.rafaelduransaez.myjastic.data.repository.LocationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideLocationRepository(
        locationHelper: LocationHelper,
        addressHelper: AddressHelper
    ): LocationRepository {
        return LocationRepositoryImpl(locationHelper, addressHelper)
    }

}