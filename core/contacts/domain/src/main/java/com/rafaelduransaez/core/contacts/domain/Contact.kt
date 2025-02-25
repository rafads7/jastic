package com.rafaelduransaez.core.contacts.domain

import com.rafaelduransaez.core.domain.extensions.empty

data class Contact(
    val id: String = String.empty(),
    val name: String = String.empty(),
    val phoneNumber: String = String.empty(),
    val email: String = String.empty()
)