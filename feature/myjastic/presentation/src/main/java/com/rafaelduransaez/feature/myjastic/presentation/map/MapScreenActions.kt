package com.rafaelduransaez.feature.myjastic.presentation.map

import com.google.android.gms.maps.model.LatLng

sealed interface MapScreenActions {
    data class SaveLocation(val location: LatLng) : MapScreenActions
    data object Cancel : MapScreenActions
}