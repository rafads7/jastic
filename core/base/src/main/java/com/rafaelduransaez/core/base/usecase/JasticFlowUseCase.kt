package com.rafaelduransaez.core.base.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

/**
 * A base UseCase that executes an operation and returns a result wrapped in a sealed class.
 */
abstract class JasticFlowUseCase<in Params, out Result> {

    /**
     * Executes the use case operation.
     */
    @Suppress("UNCHECKED_CAST")
    suspend operator fun invoke(params: Params = Unit as Params): Flow<Result> = execute(params)
        .catch { e -> handleException(e) }

    /**
     * Function to be implemented by subclasses to define the business logic.
     */
    protected abstract suspend fun execute(params: Params): Flow<Result>

    /**
     * Handles exceptions thrown during execution.
     */
    protected open fun handleException(exception: Throwable): Flow<Result> {
        throw exception // Default behavior: rethrow exception (override if needed)
    }
}
