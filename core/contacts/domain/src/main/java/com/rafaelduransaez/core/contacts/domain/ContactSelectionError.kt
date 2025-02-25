package com.rafaelduransaez.core.contacts.domain

import com.rafaelduransaez.core.domain.models.JasticFailure

sealed interface ContactSelectionError: JasticFailure {
    data object FieldNotFound : ContactSelectionError
    data object Unknown : ContactSelectionError
}