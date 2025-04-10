package com.rafaelduransaez.feature.myjastic.domain.model

import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.zero

data class JasticPointListItemUI(
    val id: Long = Long.zero,
    val alias: String = String.empty,
    val address: String = String.empty,
    val contactName: String = String.empty,
    val contactPhone: String = String.empty
)