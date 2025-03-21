package com.rafaelduransaez.core.navigation.deprecated_feature_navigator


@Deprecated("This interface will not be used till FeatureNavigator approach applied.")
interface FeatureNavigator {
    fun navigateTo(action: FeatureNavAction)
}

@Deprecated("This interface will not be used till FeatureNavigator approach applied.")
interface FeatureNavAction