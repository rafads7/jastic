package com.rafaelduransaez.core

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.rafaelduransaez.core.domain.models.JasticResult
import com.rafaelduransaez.core.domain.models.JasticResult.Companion.failure
import com.rafaelduransaez.core.domain.models.JasticResult.Companion.success
import com.rafaelduransaez.core.domain.models.NetworkError
import com.rafaelduransaez.core.domain.sources.AddressHelper
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GeocoderHelper @Inject constructor(
    context: Context,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val maxResults: Int = MAX_RESULTS,
) : AddressHelper {

    private val geocoder = Geocoder(context, Locale.getDefault())

    override suspend fun getAddressFromLatLng(latitude: Double, longitude: Double)
            : JasticResult<String, NetworkError> =
        withTimeoutOrNull(CONNECTION_TIME_OUT) {
            withContext(coroutineDispatcher) {
                suspendCancellableCoroutine<JasticResult<String, NetworkError>> { continuation ->
                    run(latitude, longitude, continuation)
                }
            }
        } ?: JasticResult.Failure(NetworkError.UNKNOWN)

    private fun run(
        latitude: Double,
        longitude: Double,
        continuation: CancellableContinuation<JasticResult<String, NetworkError>>
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude, longitude, maxResults,
                object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        handleSuccess(continuation, addresses)
                    }

                    override fun onError(errorMessage: String?) {
                        handleError(continuation, errorMessage)
                    }
                }
            )

        } else {
            @Suppress("DEPRECATION")
            val address = geocoder.getFromLocation(latitude, longitude, maxResults)?.firstOrNull()
            handleNullableResult(continuation, address)
        }
    }

    private fun handleError(
        continuation: CancellableContinuation<JasticResult<String, NetworkError>>,
        errorMessage: String?
    ) {
        val error = when {
            errorMessage.isNullOrEmpty() -> NetworkError.UNKNOWN
            errorMessage.contains("network", true) -> NetworkError.SERVER_ERROR
            errorMessage.contains("timeout", true) -> NetworkError.CONNECTION_TIMEOUT
            errorMessage.contains("connection", true) -> NetworkError.NO_CONNECTION
            errorMessage.contains("internet", true) -> NetworkError.NO_CONNECTION
            else -> NetworkError.UNKNOWN
        }
        continuation.resume(JasticResult.failure(error))
    }

    private fun handleSuccess(
        continuation: CancellableContinuation<JasticResult<String, NetworkError>>,
        addresses: MutableList<Address>
    ) {
        if (addresses.isNotEmpty()) {
            val addressLine =
                addresses.first().getAddressLine(ADDRESS_LINE_INDEX)
            continuation.resume(JasticResult.success(addressLine))
        } else {
            continuation.resume(JasticResult.failure(NetworkError.UNKNOWN))
        }
    }

    private fun handleNullableResult(
        continuation: CancellableContinuation<JasticResult<String, NetworkError>>,
        address: Address?
    ) {
        address?.let {
            continuation.resume(JasticResult.success(it.getAddressLine(ADDRESS_LINE_INDEX)))
        } ?: continuation.resume(JasticResult.failure(NetworkError.UNKNOWN))
    }

    companion object {
        const val ADDRESS_LINE_INDEX = 0
        const val MAX_RESULTS = 1
        const val CONNECTION_TIME_OUT = 5000L
    }
}
