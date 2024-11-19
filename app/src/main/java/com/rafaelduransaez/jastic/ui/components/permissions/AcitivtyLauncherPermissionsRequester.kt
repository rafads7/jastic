package com.rafaelduransaez.jastic.ui.components.permissions

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.shouldShowRationale
import com.rafaelduransaez.domain.components.common.empty
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.MainViewModel
import com.rafaelduransaez.jastic.ui.utils.findActivity
import com.rafaelduransaez.jastic.ui.utils.openSettings

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ActivityLauncherPermissionRequester(viewModel: MainViewModel) {

    val context = LocalContext.current

    val permissions = mutableListOf(ACCESS_FINE_LOCATION).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            add(ACCESS_BACKGROUND_LOCATION)
    }

    val multiplePermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        perms.keys.forEach { permission ->
            viewModel.onPermissionResult(
                permission = permission,
                isGranted = perms[permission] == true
            )
        }
    }

/*    permissions.forEach { permissionState ->
        PermissionsDialog(
            permissionTextProvider = when (permissionState.permission) {
                ACCESS_FINE_LOCATION -> LocationPermissionTextProvider(context)
                ACCESS_BACKGROUND_LOCATION -> NotificationsPermissionTextProvider(context)
                else -> return@forEach
            },
            isPermanentlyDeclined = !multiplePermission.permissions[0].status.shouldShowRationale,
            onDismiss = TODO(),
            onOkClicked = TODO(),
            onGoToAppSettingsClicked = { context.findActivity()?.openSettings() },
            modifier = TODO(),
        )
    }*/

}

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

class AllPermissionsTextProvider(private val context: Context) : PermissionTextProvider {
    override val title: String
        get() = context.getString(R.string.str_all_permissions_needed)

    override val description: String
        get() = context.getString(R.string.str_all_permissions_needed_desc)
}