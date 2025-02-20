package com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail

import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

interface JasticDestinationDetailMapper {
    fun locationToText(location: LatLng): String
}

@Singleton
class JasticDestinationDetailMapperImpl @Inject constructor() : JasticDestinationDetailMapper {

    override fun locationToText(location: LatLng) = location.toText()

    private fun LatLng.toText(): String {
        return "(${latitude}, ${longitude})"
    }
}