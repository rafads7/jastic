package com.rafaelduransaez.core

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.rafaelduransaez.core.domain.sources.AddressHelper
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GeocoderHelper @Inject constructor(
    context: Context,
    private val maxResults: Int = MAX_RESULTS
) : AddressHelper {
    private val geocoder = Geocoder(context, Locale.getDefault())

    override suspend fun getAddressFromLatLng(
        latitude: Double,
        longitude: Double
    ): String =
        suspendCancellableCoroutine { continuation ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latitude, longitude, maxResults,
                    object : Geocoder.GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            if (addresses.isNotEmpty()) {
                                val addressLine =
                                    addresses.first().getAddressLine(ADDRESS_LINE_INDEX)
                                continuation.resume(addressLine)
                            } else {
                                continuation.resumeWithException(Exception("Address not found."))
                            }
                        }

                        override fun onError(errorMessage: String?) {
                            continuation.resumeWithException(
                                Exception(
                                    errorMessage ?: "Unknown error."
                                )
                            )
                        }
                    }
                )

            } else {
                @Suppress("DEPRECATION")
                val address =
                    geocoder.getFromLocation(latitude, longitude, maxResults)?.firstOrNull()
                address?.let {
                    continuation.resume(it.getAddressLine(ADDRESS_LINE_INDEX))
                } ?: continuation.resumeWithException(Exception("Address not found."))

            }
        }


    companion object {
        const val ADDRESS_LINE_INDEX = 0
        const val MAX_RESULTS = 1
    }
}
