package com.rafaelduransaez.jastic.ui.screens

import android.Manifest
import android.location.Location
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.rafaelduransaez.domain.components.common.empty
import com.rafaelduransaez.jastic.ui.geofences.GeofenceBroadcastReceiver
import com.rafaelduransaez.jastic.ui.geofences.GeofenceHelper
import com.rafaelduransaez.jastic.ui.geofences.GeofenceHelper.Companion.CUSTOM_INTENT_GEOFENCE
import com.rafaelduransaez.jastic.ui.utils.permissions.PermissionBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun GeofencingScreen() {
    val permissions = getInitialPermissions()
    // Requires at least coarse permission
    PermissionBox(
        permissions = permissions,
        requiredPermissions = listOf(permissions.first()),
    ) {
        // For Android 10 onwards, we need background permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            PermissionBox(
                permissions = listOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
            ) {
                //GeofencingControls()

                MyJasticScreen()
                //MapScreen()
            }
        } else {
            //GeofencingControls()
            //MapScreen()
            MyJasticScreen()
        }
    }
}

@Composable
private fun GeofencingControls() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val geofenceHelper = remember { GeofenceHelper(context) }
    var geofenceTransitionEventInfo by remember { mutableStateOf(String.empty()) }

    DisposableEffect(LocalLifecycleOwner.current) {
        onDispose {
            scope.launch(Dispatchers.IO) {
                geofenceHelper.unregisterGeofence()
            }
        }
    }

    // Register a local broadcast to receive activity transition updates
    GeofenceBroadcastReceiver(systemAction = CUSTOM_INTENT_GEOFENCE) { event ->
        geofenceTransitionEventInfo = event
        Toast.makeText(context, "Geofence EVENT RECEIVED $event", Toast.LENGTH_LONG).show()
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Button(onClick = {
            geofenceHelper.addGeofence(
                "Random",
                location = Location("").apply {
                    latitude = 41.375159
                    longitude = 2.147349
                }
            )
        }) {
            Text(text = "New geofence to list")
        }

        Button(onClick = {
            geofenceHelper.registerGeofence {
                Toast.makeText(context, "Geofence registered", Toast.LENGTH_LONG).show()
            }
        }) {
            Text(text = "Register Geofence")
        }
    }
}

