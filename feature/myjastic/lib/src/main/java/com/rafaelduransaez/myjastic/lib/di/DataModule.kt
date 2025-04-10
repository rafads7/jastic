package com.rafaelduransaez.myjastic.lib.di

import com.rafaelduransaez.core.database.dao.JasticPointDao
import com.rafaelduransaez.feature.myjastic.data.repository.MyJasticRepositoryImpl
import com.rafaelduransaez.feature.myjastic.data.source.MyJasticLocalDataSource
import com.rafaelduransaez.feature.myjastic.data.source.RoomDataSource
import com.rafaelduransaez.feature.myjastic.domain.repository.MyJasticRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMyJasticLocalDataSource(jasticPointsDao: JasticPointDao): MyJasticLocalDataSource =
        RoomDataSource(jasticPointsDao)

    @Provides
    @Singleton
    fun provideMyJasticRepository(localDataSource: MyJasticLocalDataSource): MyJasticRepository =
        MyJasticRepositoryImpl(localDataSource)
}