package com.rafaelduransaez.jastic.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object NavigationModule {
/*
    @Provides
    fun provideNavigation(myJasticNavigator: MyJasticFeatureNavigator): JasticNavigator =
        JasticNavigator(myJasticNavigator)*/
}