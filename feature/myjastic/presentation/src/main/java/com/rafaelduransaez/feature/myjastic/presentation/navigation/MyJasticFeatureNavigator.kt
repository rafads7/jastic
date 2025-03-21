package com.rafaelduransaez.feature.myjastic.presentation.navigation

import com.rafaelduransaez.core.navigation.deprecated_feature_navigator.FeatureNavAction
import com.rafaelduransaez.core.navigation.deprecated_feature_navigator.FeatureNavigator

@Deprecated("This class will not be used till FeatureNavigator approach applied.")
class MyJasticFeatureNavigator : FeatureNavigator {

    override fun navigateTo(action: FeatureNavAction) {
        /*        if (action is MyJasticNavActions) {
                    when (action) {
                        MyJasticNavActions.Back -> popBackStack()
                        MyJasticNavActions.Map -> navigateToMap()
                        is MyJasticNavActions.JasticPointDetail ->
                            navigateToJasticPointDetail(action.id)
                    }
                }*/
    }
}