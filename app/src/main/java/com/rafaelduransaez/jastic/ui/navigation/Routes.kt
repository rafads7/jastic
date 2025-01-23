package com.rafaelduransaez.jastic.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.rafaelduransaez.jastic.R
import kotlinx.serialization.Serializable

@Serializable
object MyJastic

@Serializable
object Settings

@Serializable
object Maps

@Serializable
object NewJasticPoint

data class TopLevelRoute<T : Any>(val labelId: Int, val route: T, val icon: ImageVector)

val bottomNavigationRoutes = listOf(
    TopLevelRoute(R.string.str_my_jastic, MyJastic, Icons.Default.Home),
    TopLevelRoute(R.string.str_settings, Settings, Icons.Default.Settings)
)




