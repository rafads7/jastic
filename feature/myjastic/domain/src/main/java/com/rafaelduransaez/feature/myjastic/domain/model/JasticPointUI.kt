package com.rafaelduransaez.feature.myjastic.domain.model

import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.zero

data class JasticPointUI(
    val id: Long = Long.zero,
    val alias: String = String.empty,
    val contactAlias: String = String.empty,
    val contactPhone: String = String.empty,
    val message: String = String.empty,
    val destinationUI: DestinationUI = DestinationUI()
)
