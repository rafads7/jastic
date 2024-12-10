package com.rafaelduransaez.domain.models

import com.rafaelduransaez.domain.components.common.empty
import com.rafaelduransaez.domain.components.common.zero

data class JasticDestination(
    val id: Int = Int.zero(),
    val title: String = String.empty(),
    val description: String = String.empty(),
    val contact: Contact = Contact()
)