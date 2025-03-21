package com.rafaelduransaez.core.navigation.utils

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.rafaelduransaez.core.navigation.Back
import com.rafaelduransaez.core.navigation.JasticNavigable


val Double.Companion.zero: Double
    get() = 0.0

fun NavHostController.navigateTo(
    route: JasticNavigable,
    navData: Map<String, Any>,
    //navData: JasticNavData,
    options: NavOptionsBuilder.() -> Unit
) {
    navData.forEach { previousBackStackEntry?.savedStateHandle?.set(it.key, it.value)}
    //previousBackStackEntry?.savedStateHandle?.set(KEY_DATA, navData)

    when (route) {
        Back -> navigateUp()
        else -> navigate(route, navOptions(options))
    }
}

const val KEY_DATA = "data"


/*
private fun NavHostController.navigateTo(action: MyJasticNavActions) {
    when (action) {
        MyJasticNavActions.Back -> popBackStack()
        MyJasticNavActions.Map -> navigateToMap()
        is MyJasticNavActions.JasticPointDetail -> navigateToJasticPointDetail(action.id)
    }
}
*/