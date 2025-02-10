package com.rafaelduransaez.core.navigation

import androidx.navigation.NavGraphBuilder

interface FeatureNavigator {
    fun NavGraphBuilder.registerNavGraph(onBackClicked: () -> Unit,
                                         onNavigateTo: (NavigationRoutes) -> Unit)
}