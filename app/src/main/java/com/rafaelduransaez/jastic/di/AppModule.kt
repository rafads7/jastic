package com.rafaelduransaez.jastic.di

import android.app.Application
import com.rafaelduransaez.jastic.JasticApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: Application) = app as JasticApplication
}
