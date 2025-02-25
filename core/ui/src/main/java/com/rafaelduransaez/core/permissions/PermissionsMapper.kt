package com.rafaelduransaez.core.permissions

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_CONTACTS
import android.os.Build

typealias OnPermissionsNeeded = (JasticPermission, () -> Unit) -> Unit

enum class JasticPermission {
    Contacts,
    Location,
    Notifications
}

fun JasticPermission.toAndroidPermissions(): List<String> {
    return when (this) {
        JasticPermission.Contacts -> listOf(READ_CONTACTS)
        JasticPermission.Location -> listOf(ACCESS_FINE_LOCATION)
        JasticPermission.Notifications -> {
            val permissions = mutableListOf(ACCESS_NOTIFICATION_POLICY)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissions.add(POST_NOTIFICATIONS)
            }
            permissions
        }
    }
}