package com.rafaelduransaez.feature.myjastic.di

import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailMapper
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailMapperImpl
import com.rafaelduransaez.feature.myjastic.presentation.map.MapMapper
import com.rafaelduransaez.feature.myjastic.presentation.map.MapMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    fun provideJasticPointDetailMapper(): JasticPointDetailMapper {
        return JasticPointDetailMapperImpl()
    }

    @Provides
    fun provideJasticMapMapper(): MapMapper {
        return MapMapperImpl()
    }
}