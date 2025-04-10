package com.rafaelduransaez.feature.saved_destinations.lib.di

import com.rafaelduransaez.feature.saved_destinations.domain.repository.SavedDestinationsRepository
import com.rafaelduransaez.feature.saved_destinations.domain.usecases.GetSavedDestinationUseCase
import com.rafaelduransaez.feature.saved_destinations.domain.usecases.GetSavedDestinationUseCaseImpl
import com.rafaelduransaez.feature.saved_destinations.domain.usecases.GetSavedDestinationsUseCase
import com.rafaelduransaez.feature.saved_destinations.domain.usecases.GetSavedDestinationsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Singleton
    @Provides
    fun provideGetSavedDestinationsUseCase(
        repository: SavedDestinationsRepository
    ): GetSavedDestinationsUseCase {
        return GetSavedDestinationsUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideGetSavedDestinationUseCase(
        repository: SavedDestinationsRepository
    ): GetSavedDestinationUseCase {
        return GetSavedDestinationUseCaseImpl(repository)
    }
}