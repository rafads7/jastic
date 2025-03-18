package com.rafaelduransaez.core.geofencing.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        Log.e(TAG, "INSIDE GeofenceEvent")
        Toast.makeText(context, "INSIDE", LENGTH_LONG).show()

        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        geofencingEvent?.let {
            if (it.hasError()) {
                val errorMessage =
                    GeofenceStatusCodes.getStatusCodeString(it.errorCode)
                Log.e(TAG, errorMessage)
                //Toast.makeText(context, "ERROR: $errorMessage", LENGTH_LONG).show()
                return
            }

            val geofenceList = it.triggeringGeofences
            val geofenceTransition = it.geofenceTransition

            geofenceList?.forEach { geofence ->
                Log.d(TAG, "onReceive: " + geofence?.requestId)
            }

            val transitionType = it.geofenceTransition
            Log.d(TAG, "onReceive: $transitionType")

            when (transitionType) {
                Geofence.GEOFENCE_TRANSITION_ENTER -> {
                    Log.d(TAG, "onReceive: Enter")
                    Toast.makeText(context, "GEOFENCE_TRANSITION_ENTER", LENGTH_LONG).show()
                }

                Geofence.GEOFENCE_TRANSITION_EXIT -> {
                    Log.d(TAG, "onReceive: EXIT")
                    //Toast.makeText(context, "GEOFENCE_TRANSITION_EXIT", LENGTH_LONG).show()
                }

                else -> {}
            }
        } ?: {
            Log.e(TAG, "NULL GeofenceEvent")
            //Toast.makeText(context, "NULL GeofenceEvent", LENGTH_LONG).show()
        }
    }

    companion object {
        private const val TAG = "GeofenceBroadcastReceiver"
    }
}

/*
@Composable
fun GeofenceBroadcastReceiver(
    systemAction: String,
    systemEvent: (userActivity: String) -> Unit,
) {
    val TAG = "GEOFENCE_EVENT"
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

        /*        ContextCompat.registerReceiver(
                    context,
                    broadcast,
                    intentFilter,
                    ContextCompat.RECEIVER_NOT_EXPORTED
                )*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(broadcast, intentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            context.registerReceiver(broadcast, intentFilter)
        }


        onDispose {
            context.unregisterReceiver(broadcast)
        }
        /*
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcast, intentFilter)
        onDispose {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcast)
        }*/

    }
}


 */