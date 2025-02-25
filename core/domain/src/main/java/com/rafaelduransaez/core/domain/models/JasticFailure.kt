package com.rafaelduransaez.core.domain.models

import com.rafaelduransaez.core.domain.extensions.empty

interface JasticFailure {
    data object IOError : JasticFailure
    data object NotFoundError : JasticFailure
    data object NetworkError: JasticFailure
    data class UnknownError(val error: String? = String.empty()) : JasticFailure
}