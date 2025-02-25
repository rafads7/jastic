package com.rafaelduransaez.feature.myjastic.di

import com.rafaelduransaez.feature.myjastic.domain.repository.LocationRepository
import com.rafaelduransaez.feature.myjastic.domain.usecase.FetchAddressFromLocationUseCase
import com.rafaelduransaez.feature.myjastic.domain.usecase.FetchCurrentLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideFetchCurrentLocationUseCase(locationRepository: LocationRepository): FetchCurrentLocationUseCase {
        return FetchCurrentLocationUseCase(locationRepository)
    }

    @Provides
    fun provideFetchAddressFromLocationUseCase(locationRepository: LocationRepository): FetchAddressFromLocationUseCase {
        return FetchAddressFromLocationUseCase(locationRepository)
    }

}