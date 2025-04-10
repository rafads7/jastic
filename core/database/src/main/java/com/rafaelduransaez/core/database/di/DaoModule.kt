package com.rafaelduransaez.core.database.di

import com.rafaelduransaez.core.database.JasticDatabase
import com.rafaelduransaez.core.database.dao.DestinationDao
import com.rafaelduransaez.core.database.dao.JasticPointDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {

    @Provides
    fun providesJasticPointDao(database: JasticDatabase): JasticPointDao = database.jasticPointDao()


    @Provides
    fun providesDestinationDao(database: JasticDatabase): DestinationDao = database.destinationDao()

}