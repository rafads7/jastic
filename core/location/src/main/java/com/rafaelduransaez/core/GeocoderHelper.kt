package com.rafaelduransaez.core

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.util.Locale
import javax.inject.Inject

class GeocoderHelper @Inject constructor(context: Context) {
    private val geocoder = Geocoder(context, Locale.getDefault())

    @SuppressLint("NewApi")
    fun getAddressFromLatLng(location: LatLng, onResult: (String) -> Unit, onError: (String) -> Unit) {
        geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1,
            object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    if (addresses.isNotEmpty()) {
                        val addressLines = addresses.first().getAddressLine(0)
                        onResult(addressLines)
                    }
                }

                override fun onError(errorMessage: String?) {
                    onError(errorMessage ?: "Unknown error")
                }
            }
        )
    }
}
