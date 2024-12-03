package com.rafaelduransaez.jastic

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.rafaelduransaez.jastic.ui.components.common.Toast
import com.rafaelduransaez.jastic.ui.geofences.GeofenceBroadcastReceiver
import com.rafaelduransaez.jastic.ui.geofences.GeofenceHelper
import com.rafaelduransaez.jastic.ui.screens.GeofencingScreen
import com.rafaelduransaez.jastic.ui.screens.MainScreen
import com.rafaelduransaez.jastic.ui.screens.MapScreen
import com.rafaelduransaez.jastic.ui.theme.JasticTheme
import com.rafaelduransaez.jastic.ui.utils.permissions.PermissionBox

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JasticTheme {
                //MainScreen()
                GeofencingScreen()
                //MapScreen()
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    JasticTheme {
        MainScreen()
    }
}


