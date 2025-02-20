package com.rafaelduransaez.feature.myjastic.di

import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailMapper
import com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail.JasticDestinationDetailMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MyJasticModule {

    @Binds
    abstract fun bindJasticDestinationDetailMapper(mapper: JasticDestinationDetailMapperImpl)
            : JasticDestinationDetailMapper

}