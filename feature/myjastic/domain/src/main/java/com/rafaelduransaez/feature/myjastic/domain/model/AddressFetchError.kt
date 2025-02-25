package com.rafaelduransaez.feature.myjastic.domain.model

import com.rafaelduransaez.core.domain.models.JasticFailure

sealed class AddressFetchError: JasticFailure {
    data object NetworkError : AddressFetchError()
    data object NotFound : AddressFetchError()
    data object UnknownError : AddressFetchError()
}
