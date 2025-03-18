package com.rafaelduransaez.core.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions

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
        is MyJasticNavActions.JasticPointDetail -> navigateToJasticDestinationDetail(action.id)
    }
}
*/