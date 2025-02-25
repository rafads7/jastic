package com.rafaelduransaez.feature.myjastic.domain.model

import com.rafaelduransaez.core.contacts.domain.Contact
import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.zero

data class JasticDestination(
    val id: Int = Int.zero(),
    val alias: String = String.empty(),
    val contact: Contact = Contact()
)