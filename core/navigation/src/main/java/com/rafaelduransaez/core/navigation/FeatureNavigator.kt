package com.rafaelduransaez.core.navigation

import androidx.navigation.NavHostController

interface FeatureNavigator {
    fun navigateTo(action: FeatureNavAction)
}

interface FeatureNavAction