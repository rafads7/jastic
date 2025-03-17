package com.rafaelduransaez.feature.myjastic.presentation.utils

import androidx.lifecycle.SavedStateHandle
import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.zero
import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.core.extensions.getAndRemove
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_ADDRESS
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_LATITUDE
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_LONGITUDE
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.KEY_RADIUS

fun SavedStateHandle.toGeofenceLocation() = GeofenceLocation(
    getAndRemove<Double>(KEY_LATITUDE, Double.zero),
    getAndRemove<Double>(KEY_LONGITUDE, Double.zero),
    getAndRemove<String>(KEY_ADDRESS, String.empty),
    getAndRemove<Float>(KEY_RADIUS, Float.NaN)
)