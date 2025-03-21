package com.rafaelduransaez.core.base.usecase

import com.rafaelduransaez.core.base.models.JasticFailure
import com.rafaelduransaez.core.base.models.JasticResult

/**
 * A base UseCase that executes an operation and returns a result wrapped in a sealed class.
 */
abstract class JasticUseCase<in Params, out Output, out Failure : JasticFailure> {

    /**
     * Executes the use case operation.
     */
    @Suppress("UNCHECKED_CAST")
    suspend operator fun invoke(params: Params = Unit as Params): JasticResult<Output, Failure> =
//        try {
            execute(params)
/*        } catch (e: Exception) {
            handleException(e)
        }*/

    /**
     * Function to be implemented by subclasses to define the business logic.
     */
    protected abstract suspend fun execute(params: Params): JasticResult<Output, Failure>
/*
    *//**
     * Handles exceptions thrown during execution and maps them to a failure type.
     *//*
    protected open fun handleException(exception: Throwable): JasticResult<Output, Failure> {
        return when (exception) {
            is IllegalArgumentException -> JasticResult.failure(mapToFailure(JasticFailure.IOError))
            is NullPointerException -> JasticResult.failure(mapToFailure(JasticFailure.NotFoundError))
            else -> JasticResult.failure(mapToFailure(JasticFailure.UnknownError(exception.message)))
        }
    }

    *//**
     * Maps an exception to a specific failure type, allowing subclasses to override this.
     *//*
    protected open fun mapToFailure(failure: JasticFailure): Failure {
        return failure as Failure
    }*/
}
