package com.rafaelduransaez.feature.myjastic.presentation.utils

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.zero
import com.rafaelduransaez.core.extensions.getAndRemove
import com.rafaelduransaez.core.navigation.NavigationGraphs.MapGraph.NavKeys.KEY_ADDRESS
import com.rafaelduransaez.core.navigation.NavigationGraphs.MapGraph.NavKeys.KEY_LATITUDE
import com.rafaelduransaez.core.navigation.NavigationGraphs.MapGraph.NavKeys.KEY_LONGITUDE
import com.rafaelduransaez.core.navigation.NavigationGraphs.MapGraph.NavKeys.KEY_RADIUS
import com.rafaelduransaez.feature.map.domain.model.GeofenceLocation
import kotlinx.coroutines.delay

fun SavedStateHandle.toGeofenceLocation() =
    GeofenceLocation(
        getAndRemove<Double>(KEY_LATITUDE, Double.zero),
        getAndRemove<Double>(KEY_LONGITUDE, Double.zero),
        getAndRemove<String>(KEY_ADDRESS, String.empty),
        getAndRemove<Float>(KEY_RADIUS, Float.NaN)
    )