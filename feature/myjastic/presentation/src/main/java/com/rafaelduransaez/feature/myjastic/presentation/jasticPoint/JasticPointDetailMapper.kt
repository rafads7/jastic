package com.rafaelduransaez.feature.myjastic.presentation.jasticPoint

import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

interface JasticPointDetailMapper {
    fun locationToText(location: LatLng): String
}

@Singleton
class JasticPointDetailMapperImpl @Inject constructor() : JasticPointDetailMapper {

    override fun locationToText(location: LatLng) = location.toText()

    private fun LatLng.toText(): String {
        return "(${latitude}, ${longitude})"
    }
}