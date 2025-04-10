package com.rafaelduransaez.core.base.models

sealed class JasticResult<out S, out F: JasticFailure> {
    data class Success<out S>(val data: S) : JasticResult<S, Nothing>()
    data class Failure<out F : JasticFailure>(val error: F) : JasticResult<Nothing, F>()

    fun isSuccess(): Boolean = this is Success
    fun isFailure(): Boolean = this is Failure

    fun getOrNull(): S? = (this as? Success)?.data
    fun errorOrNull(): F? = (this as? Failure)?.error

    inline fun fold(
        onSuccess: (S) -> Unit,
        onFailure: (F) -> Unit
    ) {
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(error)
        }
    }

    companion object {
        fun <T> success(value: T): Success<T> {
            return Success(value)
        }

        fun <T: JasticFailure> failure(value: T): Failure<T> {
            return Failure(value)
        }
    }
}
