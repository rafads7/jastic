package com.rafaelduransaez.jastic.ui.geofences

/*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent!!.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...")
            return
        }

        val geofenceList = geofencingEvent.triggeringGeofences
        for (geofence in geofenceList!!) {
            Log.d(TAG, "onReceive: " + geofence.requestId)
        }
        //        Location location = geofencingEvent.getTriggeringLocation();
        val transitionType = geofencingEvent.geofenceTransition
        Log.d(TAG, "onReceive: $transitionType")

        when (transitionType) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                Log.d(TAG, "onReceive: Enter")
                Toast.makeText(context.applicationContext, "GEOFENCE_TRANSITION_ENTER", Toast.LENGTH_SHORT).show()
            }

            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                Log.d(TAG, "onReceive: DWELL")
                Toast.makeText(context.applicationContext, "GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_SHORT).show()
            }

            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                Log.d(TAG, "onReceive: EXIT")
                Toast.makeText(context.applicationContext, "GEOFENCE_TRANSITION_EXIT", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TAG = "GeofenceBroadcastReceiv"
    }
}*/


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

@Composable
fun GeofenceBroadcastReceiver(
    systemAction: String,
    systemEvent: (userActivity: String) -> Unit,
) {
    val TAG = "GeofenceBroadcastReceiver"
    val context = LocalContext.current
    val currentSystemOnEvent by rememberUpdatedState(systemEvent)

    DisposableEffect(context, systemAction) {
        val intentFilter = IntentFilter(systemAction)
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val geofencingEvent = intent?.let { GeofencingEvent.fromIntent(it) } ?: return

                if (geofencingEvent.hasError()) {
                    val errorMessage =
                        GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
                    Log.e(TAG, "onReceive: $errorMessage")
                    return
                }

                val alertString = "Geofence Alert :" +
                        " Trigger ${geofencingEvent.triggeringGeofences}" +
                        " Transition ${geofencingEvent.geofenceTransition}"
                Log.d(TAG, alertString)
                currentSystemOnEvent(alertString)
            }
        }

        ContextCompat.registerReceiver(
            context,
            broadcast,
            intentFilter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        onDispose {
            context.unregisterReceiver(broadcast)
        }
        /*        LocalBroadcastManager.getInstance(context).registerReceiver(broadcast, intentFilter)
                onDispose {
                    LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcast)
                }*/

    }
}
