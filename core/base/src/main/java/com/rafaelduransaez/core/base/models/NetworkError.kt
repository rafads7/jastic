package com.rafaelduransaez.core.base.models

enum class NetworkError : JasticFailure {
    CONNECTION_TIMEOUT,
    SERVER_ERROR,
    TOO_MANY_REQUESTS,
    NO_CONNECTION,
    UNKNOWN
}