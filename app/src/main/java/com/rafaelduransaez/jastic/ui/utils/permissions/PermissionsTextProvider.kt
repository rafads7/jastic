package com.rafaelduransaez.jastic.ui.utils.permissions

import android.content.Context
import com.rafaelduransaez.domain.components.common.empty
import com.rafaelduransaez.jastic.R


interface PermissionTextProvider {
    val title: String
    val description: String
}

class LocationPermissionTextProvider(private val context: Context) : PermissionTextProvider {
    override val title: String
        get() = context.getString(R.string.str_location_permissions_needed)

    override val description: String
        get() = context.getString(R.string.str_location_permissions_needed_desc)
}

class NotificationsPermissionTextProvider(private val context: Context) : PermissionTextProvider {
    override val title: String
        get() = context.getString(R.string.str_notifications_permissions_needed)

    override val description: String
        get() = context.getString(R.string.str_notifications_permissions_needed_desc)
}

class UnknownPermissionTextProvider() : PermissionTextProvider {
    override val title: String
        get() = String.empty()

    override val description: String
        get() = String.empty()
}

class ContactsPermissionTextProvider(private val context: Context): PermissionTextProvider {
    override val title: String
        get() = context.getString(R.string.str_contacts_permission_needed)
    override val description: String
        get() = context.getString(R.string.str_contacts_permission_needed_desc)
}

class AllPermissionsTextProvider(private val context: Context) : PermissionTextProvider {
    override val title: String
        get() = context.getString(R.string.str_all_permissions_needed)

    override val description: String
        get() = context.getString(R.string.str_all_permissions_needed_desc)
}
