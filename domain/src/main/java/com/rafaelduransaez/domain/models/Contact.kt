package com.rafaelduransaez.domain.models

import com.rafaelduransaez.domain.components.common.empty

data class Contact(
    val name: String = String.empty(),
    val phone: String = String.empty(),
    val email: String = String.empty()
)