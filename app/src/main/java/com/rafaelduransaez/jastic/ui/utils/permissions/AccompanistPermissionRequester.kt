package com.rafaelduransaez.jastic.ui.utils.permissions

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_CONTACTS
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.components.common.Toast
import com.rafaelduransaez.jastic.ui.components.jAlertDialog.JAlertDialog
import com.rafaelduransaez.jastic.ui.utils.findActivity
import com.rafaelduransaez.jastic.ui.utils.openSettings
import com.rafaelduransaez.jastic.ui.utils.permissions.Companion.Companion.MIN_PERMISSIONS
import com.rafaelduransaez.jastic.ui.utils.toFalse
import com.rafaelduransaez.jastic.ui.utils.toTrue

@Composable
fun AccompanistPermissionRequester(
    permissions: List<String> = emptyList(),
    onAllGranted: @Composable () -> Unit = {},
) {
    MultiplePermissionRequester(permissions, onAllGranted = onAllGranted)
    /*    if (permissions.size == 1) {
            SinglePermissionRequester(permission = permissions.first(), onGranted = onAllGranted)
        } else {
            MultiplePermissionRequester(permissions, onAllGranted = onAllGranted)
        }*/
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SinglePermissionRequester(permission: String, onGranted: @Composable () -> Unit) {
    val context = LocalContext.current
    val showAlertDialog = remember { mutableStateOf(false) }
    var hasRequestedPermission by rememberSaveable { mutableStateOf(false) }
    var permissionRequestCompleted by rememberSaveable { mutableStateOf(false) }
    val permissionState = rememberPermissionState(permission = permission)

    LaunchedEffect(permissionState.status) {
        if (hasRequestedPermission) {
            permissionRequestCompleted = true
        }
    }
    when (val status = permissionState.status) {
        is PermissionStatus.Granted -> {
            onGranted()
        }

        is PermissionStatus.Denied -> {
            if (permissionRequestCompleted) {
                if (showAlertDialog.value) {
                    if (status.shouldShowRationale) {
                        RationaleDialog(
                            permissionTextProvider = getPermissionTextProvider(
                                permissionState.permission, context
                            ),
                            onRationaleActionClicked = {
                                showAlertDialog.toFalse()
                                permissionState.launchPermissionRequest()
                                hasRequestedPermission = true
                            },
                            onDismissActionClicked = { showAlertDialog.toFalse() }
                        )
                    } else {
                        GoToAppSettingsDialog(
                            showAlertDialog = showAlertDialog,
                            permissionTextProvider = getPermissionTextProvider(
                                permissionState.permission, context
                            )
                        )
                    }
                }
            } else {
                if (showAlertDialog.value)
                    RationaleDialog(
                        permissionTextProvider = getPermissionTextProvider(
                            permissionState.permission, context
                        ),
                        onRationaleActionClicked = {
                            showAlertDialog.toFalse()
                            permissionState.launchPermissionRequest()
                            hasRequestedPermission = true
                        },
                        onDismissActionClicked = { showAlertDialog.toFalse() }
                    )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MultiplePermissionRequester(permissions: List<String>, onAllGranted: @Composable () -> Unit) {

    Log.e("MultiplePermissionRequester", "INICIO")
    val context = LocalContext.current
    val showAlertDialog = remember { mutableStateOf(false) }
    var hasRequestedPermissions by rememberSaveable { mutableStateOf(false) }
    var permissionRequestCompleted by rememberSaveable { mutableStateOf(false) }
    val permissionsState = rememberMultiplePermissionsState(permissions)

    LaunchedEffect(permissionsState.revokedPermissions) {
        Log.e("MultiplePermissionRequester", "LAUNCH EFFECT")
        if (hasRequestedPermissions) {
            permissionRequestCompleted = true
        }
    }

    when {
        permissionsState.allPermissionsGranted -> {
            showAlertDialog.toFalse()
            if (hasRequestedPermissions)
                Toast(R.string.str_thanks_for_permissions)
            onAllGranted()
        }

        permissionsState.shouldShowRationale -> {
            showAlertDialog.toTrue()
            if (showAlertDialog.value)
                RationaleDialog(
                    permissionTextProvider = getPermissionsTextProvider(permissionsState, context),
                    onRationaleActionClicked = {
                        showAlertDialog.toFalse()
                        Log.e("MultiplePermissionRequester", "DESPUES DEL FALSE")
                        permissionsState.launchMultiplePermissionRequest()
                        hasRequestedPermissions = true
                    }
                )
        }
        else -> {
            showAlertDialog.toTrue()
            if (permissionRequestCompleted) {
                if (showAlertDialog.value)
                    GoToAppSettingsDialog(
                        permissionTextProvider = getPermissionsTextProvider(permissionsState, context),
                        showAlertDialog = showAlertDialog,
                    )
            } else {
                if (showAlertDialog.value)
                    RationaleDialog(
                        permissionTextProvider = getPermissionsTextProvider(
                            permissionsState,
                            context
                        ),
                        onRationaleActionClicked = {
                            showAlertDialog.toFalse()
                            Log.e("MultiplePermissionRequester", "DESPUES DEL FALSE")
                            permissionsState.launchMultiplePermissionRequest()
                            hasRequestedPermissions = true
                        }
                    )
            }
        }
    }
}

@Composable
fun GoToAppSettingsDialog(
    permissionTextProvider: PermissionTextProvider,
    showAlertDialog: MutableState<Boolean>,
    onDismissActionClicked: (() -> Unit)? = null
) {
    val context = LocalContext.current

    JAlertDialog(
        title = permissionTextProvider.title,
        description = permissionTextProvider.description,
        confirmButtonTextId = R.string.str_go_to_settings,
        onConfirm = {
            showAlertDialog.toFalse()
            context.findActivity()?.openSettings()
        },
        onDismiss = onDismissActionClicked
    )
}

@Composable
fun RationaleDialog(
    permissionTextProvider: PermissionTextProvider,
    onRationaleActionClicked: () -> Unit,
    onDismissActionClicked: (() -> Unit)? = null
) {
    JAlertDialog(
        title = permissionTextProvider.title,
        description = permissionTextProvider.description,
        onConfirm = { onRationaleActionClicked() },
        onDismiss = onDismissActionClicked
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
        getPermissionTextProvider(
            permissionsState.revokedPermissions.firstOrNull()?.permission,
            context
        )
    }

private fun getPermissionTextProvider(
    permission: String?,
    context: Context
): PermissionTextProvider =
    when (permission) {
        ACCESS_FINE_LOCATION,
        ACCESS_BACKGROUND_LOCATION -> LocationPermissionTextProvider(context)

        POST_NOTIFICATIONS,
        ACCESS_NOTIFICATION_POLICY -> NotificationsPermissionTextProvider(context)

        READ_CONTACTS -> ContactsPermissionTextProvider(context)

        else -> UnknownPermissionTextProvider()
    }

class Companion {
    companion object {
        const val MIN_PERMISSIONS = 2
    }
}

