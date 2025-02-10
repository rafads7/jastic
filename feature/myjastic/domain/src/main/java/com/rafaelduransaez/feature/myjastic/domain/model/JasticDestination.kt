package com.rafaelduransaez.feature.myjastic.domain.model

import com.rafaelduransaez.core.utils.extensions.empty
import com.rafaelduransaez.core.utils.extensions.zero

data class JasticDestination(
    val id: Int = Int.zero(),
    val title: String = String.empty(),
    val description: String = String.empty(),
    val contact: Contact = Contact()
)