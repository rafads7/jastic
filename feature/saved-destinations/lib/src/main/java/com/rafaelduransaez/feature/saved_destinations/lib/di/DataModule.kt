package com.rafaelduransaez.feature.saved_destinations.lib.di

import com.rafaelduransaez.core.database.dao.DestinationDao
import com.rafaelduransaez.feature.saved_destinations.data.repository.SavedDestinationsRepositoryImpl
import com.rafaelduransaez.feature.saved_destinations.data.source.RoomDataSource
import com.rafaelduransaez.feature.saved_destinations.data.source.SavedDestinationsLocalDataSource
import com.rafaelduransaez.feature.saved_destinations.domain.repository.SavedDestinationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideSavedDestinationsLocalDataSource(
        destinationDao: DestinationDao
    ): SavedDestinationsLocalDataSource = RoomDataSource(destinationDao)

    @Provides
    @Singleton
    fun provideSavedDestinationsRepository(
        localDataSource: SavedDestinationsLocalDataSource
    ): SavedDestinationsRepository = SavedDestinationsRepositoryImpl(localDataSource)

}