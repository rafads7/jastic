package com.rafaelduransaez.jastic.ui.utils.permissions

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_CONTACTS
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.components.common.Toast
import com.rafaelduransaez.jastic.ui.components.jAlertDialog.JAlertDialog
import com.rafaelduransaez.jastic.ui.utils.extensions.findActivity
import com.rafaelduransaez.jastic.ui.utils.extensions.openSettings

private const val MIN_PERMISSIONS = 2

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsRequester(permissions: List<String>, onAllGranted: @Composable () -> Unit = {}) {

    var showAlertDialog by rememberSaveable { mutableStateOf(false) }
    var allGranted by rememberSaveable { mutableStateOf(false) }
    var permissionsAlreadyRequested by rememberSaveable { mutableStateOf(false) }
    val permissionsState = rememberMultiplePermissionsState(permissions) {
        permissionsAlreadyRequested = true
    }

    LifecycleResumeEffect(Unit) {
        with(permissionsState.allPermissionsGranted) {
            showAlertDialog = !this
            allGranted = this
        }
        onPauseOrDispose {
            showAlertDialog = false
        }
    }

    if (allGranted) {
        if (permissionsAlreadyRequested) {
            Toast(R.string.str_thanks_for_permissions)
            permissionsAlreadyRequested = false
        }
        onAllGranted()
        allGranted = false
        return
    }

    if (showAlertDialog) {
        PermissionsDialog(
            permissionsState = permissionsState,
            permissionsAlreadyRequested = permissionsAlreadyRequested,
            onConfirmToRationale = {
                showAlertDialog = false
                permissionsState.launchMultiplePermissionRequest()
            },
            hideDialogAction = { showAlertDialog = false }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsDialog(
    permissionsState: MultiplePermissionsState,
    permissionsAlreadyRequested: Boolean,
    onConfirmToRationale: () -> Unit,
    hideDialogAction: () -> Unit,
) {
    val textProvider = getPermissionsTextProvider(permissionsState, LocalContext.current)
    when {
        permissionsState.shouldShowRationale -> RationaleDialog(textProvider, onConfirmToRationale)
        permissionsAlreadyRequested -> GoToAppSettingsAppDialog(textProvider, hideDialogAction)
        else -> RationaleDialog(textProvider, onConfirmToRationale)
    }
}

@Composable
fun GoToAppSettingsAppDialog(
    permissionTextProvider: PermissionTextProvider,
    onConfirm: () -> Unit
) {
    val context = LocalContext.current
    JAlertDialog(
        title = permissionTextProvider.title,
        description = permissionTextProvider.description,
        confirmButtonTextId = R.string.str_go_to_settings,
        onConfirm = {
            onConfirm()
            context.findActivity()?.openSettings()
        }
    )
}

@Composable
fun RationaleDialog(
    permissionTextProvider: PermissionTextProvider,
    onConfirm: () -> Unit
) {
    JAlertDialog(
        title = permissionTextProvider.title,
        description = permissionTextProvider.description,
        onConfirm = onConfirm
    )
}

@OptIn(ExperimentalPermissionsApi::class)
private fun getPermissionsTextProvider(
    permissionsState: MultiplePermissionsState,
    context: Context
): PermissionTextProvider =
    if (permissionsState.revokedPermissions.size >= MIN_PERMISSIONS) {
        AllPermissionsTextProvider(context)
    } else {
        when (permissionsState.revokedPermissions.firstOrNull()?.permission) {
            ACCESS_FINE_LOCATION,
            ACCESS_BACKGROUND_LOCATION -> LocationPermissionTextProvider(context)

            POST_NOTIFICATIONS,
            ACCESS_NOTIFICATION_POLICY -> NotificationsPermissionTextProvider(context)

            READ_CONTACTS -> ContactsPermissionTextProvider(context)

            else -> UnknownPermissionTextProvider()
        }
    }

