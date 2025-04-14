package com.rafaelduransaez.jastic.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.jastic.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class TopLevelRoute<T: NavigationGraphs>(
    val labelId: Int,
    val navRoute: T,
    @Transient val icon: ImageVector = Icons.Filled.Home,
    @Transient val unselectedIcon: ImageVector = Icons.Outlined.Home
) {

    @Serializable
    data object MyJasticRoute :
        TopLevelRoute<NavigationGraphs.MyJasticGraph>(
            labelId = R.string.str_jastic_my_jastic,
            navRoute = NavigationGraphs.MyJasticGraph,
            icon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        )
    @Serializable
    data object SavedDestinationsRoute :
        TopLevelRoute<NavigationGraphs.SavedDestinationsGraph>(
            labelId = R.string.str_jastic_destinations,
            navRoute = NavigationGraphs.SavedDestinationsGraph,
            icon = Icons.Filled.Place,
            unselectedIcon = Icons.Outlined.Place
        )

    @Serializable
    data object SettingsRoute :
        TopLevelRoute<NavigationGraphs.SettingsGraph>(
            labelId = R.string.str_jastic_settings,
            navRoute = NavigationGraphs.SettingsGraph,
            icon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        )
}