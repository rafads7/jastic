package com.rafaelduransaez.feature.myjastic.di

import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailMapper
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailMapperImpl
import com.rafaelduransaez.feature.myjastic.presentation.map.MapMapper
import com.rafaelduransaez.feature.myjastic.presentation.map.MapMapperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    fun provideJasticDestinationDetailMapper(): JasticDestinationDetailMapper {
        return JasticDestinationDetailMapperImpl()
    }

    @Provides
    fun provideJasticMapMapper(): MapMapper {
        return MapMapperImpl()
    }
}