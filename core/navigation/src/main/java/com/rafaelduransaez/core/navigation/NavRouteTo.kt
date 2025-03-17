package com.rafaelduransaez.core.navigation

import androidx.navigation.NavOptionsBuilder

typealias NavRouteTo = (JasticNavigable, Map<String, Any>, NavOptionsBuilder.() -> Unit) -> Unit
//typealias NavRouteTo = (JasticNavigable, JasticNavData, NavOptionsBuilder.() -> Unit) -> Unit

operator fun NavRouteTo.invoke(route: JasticNavigable) {
    //this(route, JasticNavData.Empty) { }
    this(route, emptyMap()) { }
}

operator fun NavRouteTo.invoke(route: JasticNavigable, navData: Map<String, Any>) {
//operator fun NavRouteTo.invoke(route: JasticNavigable, navData: JasticNavData) {
    this(route, navData) { }
}

operator fun NavRouteTo.invoke(
    route: JasticNavigable,
    //navData: JasticNavData,
    navData: Map<String, Any>,
    options: NavOptionsBuilder.() -> Unit
) {
    this(route, navData, options)
}