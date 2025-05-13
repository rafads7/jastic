package com.rafaelduransaez.core.base.models

interface JasticFailure

sealed interface JasticError: JasticFailure {
    data class NotFoundError(val message: String) : JasticError
    data class IOError(val message: String) : JasticError
    data class UnknownError(val message: String) : JasticError
}