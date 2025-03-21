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

        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        geofencingEvent?.let {
            if (it.hasError()) {
                val errorMessage = GeofenceStatusCodes.getStatusCodeString(it.errorCode)
                Log.e(TAG, errorMessage)
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