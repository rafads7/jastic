package com.rafaelduransaez.core.base.usecase

import com.rafaelduransaez.core.base.models.JasticError
import com.rafaelduransaez.core.base.models.JasticFailure
import com.rafaelduransaez.core.base.models.JasticResult

/**
 * A base UseCase that executes an operation and returns a result wrapped in a sealed class.
 */
@Suppress("UNCHECKED_CAST")
abstract class JasticUseCase<in Params, out Output, out Failure : JasticFailure> {

    /**
     * Executes the use case operation.
     */
    suspend operator fun invoke(params: Params = Unit as Params): JasticResult<Output, Failure> =
        try {
            execute(params)
        } catch (e: Exception) {
            handleException(e)
        }

    /**
     * Function to be implemented by subclasses to define the business logic.
     */
    protected abstract suspend fun execute(params: Params): JasticResult<Output, Failure>

    /**
     * Handles exceptions thrown during execution and maps them to a failure type.
     */
    protected open fun handleException(exception: Throwable): JasticResult<Output, Failure> {
        return JasticResult.failure(mapToFailure(exception))
    }

    /**
     * Maps an exception to a specific failure type, allowing subclasses to override this.
     */
    protected open fun mapToFailure(throwable: Throwable): Failure {
        return when (throwable) {
            is IllegalArgumentException -> JasticError.IOError("${throwable.message}")
            is NullPointerException -> JasticError.NotFoundError("${throwable.message}")
            else -> JasticError.UnknownError("${throwable.message}")
        } as Failure
    }
}
