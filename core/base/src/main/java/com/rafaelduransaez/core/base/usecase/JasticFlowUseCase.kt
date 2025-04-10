package com.rafaelduransaez.core.base.usecase

import com.rafaelduransaez.core.base.models.JasticFailure
import com.rafaelduransaez.core.base.models.JasticResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class JasticFlowUseCase<in Params, out Result, out Failure : JasticFailure> {

    /**
     * Executes the use case operation.
     */
    @Suppress("UNCHECKED_CAST")
    suspend operator fun invoke(params: Params = Unit as Params): Flow<JasticResult<Result, Failure>> =
        execute(params)
            .catch { e -> handleException(e) }

    /**
     * Function to be implemented by subclasses to define the business logic.
     */
    protected abstract suspend fun execute(params: Params): Flow<JasticResult<Result, Failure>>

    /**
     * Handles exceptions thrown during execution.
     */
    protected open fun handleException(exception: Throwable): Flow<Result> {
        throw exception // Default behavior: rethrow exception (override if needed)
    }
}
