package com.rafaelduransaez.feature.myjastic.domain.model

import com.rafaelduransaez.core.utils.extensions.empty


data class Contact(
    val name: String = String.empty(),
    val phone: String = String.empty(),
    val email: String = String.empty()
)