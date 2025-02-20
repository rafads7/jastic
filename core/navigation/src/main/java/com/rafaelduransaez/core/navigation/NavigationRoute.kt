@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.rafaelduransaez.core.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

interface JasticNavigable

@Serializable
data object Back/*(val navData: Map<String, @Contextual Any> = emptyMap())*/ : JasticNavigable

@Serializable
sealed interface NavigationRoute : JasticNavigable {

    @Serializable
    data object Settings : NavigationRoute
}

typealias NavRouteTo = (JasticNavigable, Map<String, Any>, NavOptionsBuilder.() -> Unit) -> Unit

operator fun NavRouteTo.invoke(route: JasticNavigable) {
    this(route, emptyMap()) { }
}

operator fun NavRouteTo.invoke(route: JasticNavigable, navData: Map<String, Any>) {
    this(route, navData) { }
}

operator fun NavRouteTo.invoke(
    route: JasticNavigable,
    navData: Map<String, Any>,
    options: NavOptionsBuilder.() -> Unit
) {
    this(route, navData, options)
}




