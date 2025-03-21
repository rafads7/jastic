package com.rafaelduransaez.core.permissions

import android.content.Context
import com.rafaelduransaez.core.domain.extensions.empty

interface PermissionTextProvider {
    val title: String
    val description: String
}

class LocationPermissionTextProvider(private val context: Context) : PermissionTextProvider {
    override val title: String
        get() = context.getString(R.string.str_core_ui_permissions_location_permissions_needed)

    override val description: String
        get() = context.getString(R.string.str_core_ui_permissions_location_permissions_needed_desc)
}

class NotificationsPermissionTextProvider(private val context: Context) : PermissionTextProvider {
    override val title: String
        get() = context.getString(R.string.str_core_ui_permissions_notifications_permissions_needed)

    override val description: String
        get() = context.getString(R.string.str_core_ui_permissions_notifications_permissions_needed_desc)
}

class UnknownPermissionTextProvider : PermissionTextProvider {
    override val title: String
        get() = String.empty

    override val description: String
        get() = String.empty
}

class ContactsPermissionTextProvider(private val context: Context): PermissionTextProvider {
    override val title: String
        get() = context.getString(R.string.str_core_ui_permissions_contacts_permission_needed)
    override val description: String
        get() = context.getString(R.string.str_core_ui_permissions_contacts_permission_needed_desc)
}

class AllPermissionsTextProvider(private val context: Context) : PermissionTextProvider {
    override val title: String
        get() = context.getString(R.string.str_core_ui_permissions_all_permissions_needed)

    override val description: String
        get() = context.getString(R.string.str_core_ui_permissions_all_permissions_needed_desc)
}
