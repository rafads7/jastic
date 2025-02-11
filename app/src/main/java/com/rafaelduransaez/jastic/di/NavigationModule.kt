package com.rafaelduransaez.jastic.di

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticFeatureNavigator
import com.rafaelduransaez.jastic.navigation.JasticNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NavigationModule {

    @Provides
    @Singleton
    fun provideMyJasticFeatureNavigator(): MyJasticFeatureNavigator = MyJasticFeatureNavigator()


    @Provides
    @Singleton
    fun provideNavigation(myJasticFeatureNavigator: MyJasticFeatureNavigator): JasticNavigator =
        JasticNavigator(myJasticFeatureNavigator)
}