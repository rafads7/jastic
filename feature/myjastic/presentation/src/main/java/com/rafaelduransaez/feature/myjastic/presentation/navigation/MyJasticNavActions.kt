package com.rafaelduransaez.feature.myjastic.presentation.navigation

import com.rafaelduransaez.core.navigation.FeatureNavAction
import com.rafaelduransaez.core.utils.extensions.negative

@Deprecated("This class will not be used while OnRouteTo is used.")
sealed class MyJasticNavActions : FeatureNavAction {
    data object Back : MyJasticNavActions()
    data object Map : MyJasticNavActions()
    data class JasticDestinationDetail(val id: Int = Int.negative()) : MyJasticNavActions()
}
