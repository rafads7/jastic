package com.rafaelduransaez.core.permissions

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_CONTACTS
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rafaelduransaez.core.components.jAlertDialog.JAlertDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val MULTIPLE_PERMISSIONS = 2

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsRequester(
    permissions: List<String>,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onCancelRequest: () -> Unit,
    onAllGranted: () -> Unit = {},
) {
    val context = LocalContext.current

    var permissionsRequested by rememberSaveable { mutableStateOf(false) }
    val permissionsState = rememberMultiplePermissionsState(permissions) {
        permissionsRequested = true
    }
    var permissionsDialogState by rememberSaveable(stateSaver = PermissionsDialogStateSaver) {
        mutableStateOf(PermissionsDialogState.None)
    }

    LifecycleResumeEffect(permissionsState) {
        if (permissionsState.revokedPermissions == permissions)
            permissionsRequested = false

        if (permissionsState.permissions.isNotEmpty()) {
            coroutineScope.launch {
                snapshotFlow { permissionsState }
                    .collect { state ->
                        permissionsDialogState = when {
                            state.allPermissionsGranted -> PermissionsDialogState.Granted
                            state.shouldShowRationale || !permissionsRequested -> PermissionsDialogState.Rationale
                            else -> PermissionsDialogState.Settings
                        }
                    }
            }
        } else {
            permissionsDialogState = PermissionsDialogState.None
        }

        onPauseOrDispose { }
    }

    when (permissionsDialogState) {
        PermissionsDialogState.Granted -> {
            if (permissionsState.allPermissionsGranted)
                onAllGranted()
            else
                permissionsRequested = false
        }

        PermissionsDialogState.Rationale -> {
            RationaleDialog(
                permissionTextProvider = getPermissionsTextProvider(permissionsState, context),
                onConfirm = {
                    permissionsDialogState = PermissionsDialogState.None
                    permissionsState.launchMultiplePermissionRequest()
                }
            )
        }

        PermissionsDialogState.Settings -> {
            GoToAppSettingsDialog(
                permissionTextProvider = getPermissionsTextProvider(permissionsState, context),
                onConfirm = {
                    permissionsDialogState = PermissionsDialogState.None
                    context.openAppSettings()
                },
                onDismiss = {
                    permissionsDialogState = PermissionsDialogState.None
                    onCancelRequest()
                }
            )
        }

        PermissionsDialogState.None -> {
            Unit
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun getPermissionsTextProvider(
    permissionsState: MultiplePermissionsState,
    context: Context
): PermissionTextProvider =
    if (permissionsState.revokedPermissions.size >= MULTIPLE_PERMISSIONS) {
        AllPermissionsTextProvider(context)
    } else {
        when (permissionsState.revokedPermissions.firstOrNull()?.permission) {
            ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION -> LocationPermissionTextProvider(
                context
            )

            POST_NOTIFICATIONS, ACCESS_NOTIFICATION_POLICY -> NotificationsPermissionTextProvider(
                context
            )

            READ_CONTACTS -> ContactsPermissionTextProvider(context)
            else -> UnknownPermissionTextProvider()
        }
    }

@Composable
internal fun RationaleDialog(
    permissionTextProvider: PermissionTextProvider,
    onConfirm: () -> Unit
) {

    JAlertDialog(
        title = permissionTextProvider.title,
        description = permissionTextProvider.description,
        confirmButtonTextId = R.string.str_core_ui_permissions_str_ok,
        onConfirm = onConfirm
    )
}

@Composable
internal fun GoToAppSettingsDialog(
    permissionTextProvider: PermissionTextProvider,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    JAlertDialog(
        title = permissionTextProvider.title,
        description = permissionTextProvider.description,
        confirmButtonTextId = R.string.str_core_ui_permissions_go_to_settings,
        onConfirm = onConfirm,
        onDismiss = onDismiss
    )
}
