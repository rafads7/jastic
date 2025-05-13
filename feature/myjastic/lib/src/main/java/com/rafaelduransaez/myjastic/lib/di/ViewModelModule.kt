package com.rafaelduransaez.myjastic.lib.di

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.rafaelduransaez.feature.myjastic.domain.qualifiers.JasticPointId
import com.rafaelduransaez.feature.myjastic.presentation.navigation.JasticPointDetail
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    @JasticPointId
    fun provideJasticPointId(savedStateHandle: SavedStateHandle): Long =
        savedStateHandle.toRoute<JasticPointDetail>().jasticPointId

}