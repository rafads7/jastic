package com.rafaelduransaez.jastic.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.jastic.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class TopLevelRoute<T: NavigationGraphs>(
    val labelId: Int,
    val route: T,
    @Transient val icon: ImageVector = Icons.Filled.Home,
    @Transient val unselectedIcon: ImageVector = Icons.Outlined.Home
) {

    @Serializable
    data object MyJasticRoute :
        TopLevelRoute<NavigationGraphs.MyJasticGraph>(
            labelId = R.string.str_my_jastic,
            route = NavigationGraphs.MyJasticGraph,
            icon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        )

    @Serializable
    data object SettingsRoute :
        TopLevelRoute<NavigationGraphs.SettingsGraph>(
            labelId = R.string.str_settings,
            route = NavigationGraphs.SettingsGraph,
            icon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        )
}