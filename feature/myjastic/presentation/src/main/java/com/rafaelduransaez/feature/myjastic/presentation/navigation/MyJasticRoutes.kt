package com.rafaelduransaez.feature.myjastic.presentation.navigation

import com.rafaelduransaez.core.domain.extensions.zero
import com.rafaelduransaez.core.navigation.JasticNavigable
import kotlinx.serialization.Serializable

@Serializable
data object MyJastic: JasticNavigable

@Serializable
data class JasticPointDetail(val jasticPointId: Long = Long.zero): JasticNavigable
