package com.rafaelduransaez.jastic.navigation

import androidx.navigation.NavHostController
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticFeatureNavigator
import javax.inject.Inject

open class JasticNavigator @Inject constructor(
    internal val myJasticNavigator: MyJasticFeatureNavigator
    //val settingsNavigator: SettingsFeatureNavigator
){

    private var navController: NavHostController? = null

    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }
}
