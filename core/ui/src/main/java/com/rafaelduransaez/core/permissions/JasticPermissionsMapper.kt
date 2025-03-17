package com.rafaelduransaez.core.permissions

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_CONTACTS
import android.os.Build
import androidx.compose.runtime.Composable

typealias OnPermissionNeeded = (JasticPermission,  () -> Unit) -> Unit

enum class JasticPermission {
    Contacts,
    Location,
    Notifications
}

fun JasticPermission.toAndroidPermissions(): List<String> {
    return when (this) {
        JasticPermission.Contacts -> listOf(READ_CONTACTS)
        JasticPermission.Location -> {
            val permissions = mutableListOf(ACCESS_FINE_LOCATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissions.add(0, ACCESS_BACKGROUND_LOCATION)
            }
            permissions
        }
        JasticPermission.Notifications -> {
            val permissions = mutableListOf(ACCESS_NOTIFICATION_POLICY)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissions.add(POST_NOTIFICATIONS)
            }
            permissions
        }
    }
}