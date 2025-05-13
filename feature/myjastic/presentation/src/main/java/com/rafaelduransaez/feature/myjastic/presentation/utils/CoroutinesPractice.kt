package com.rafaelduransaez.feature.myjastic.presentation.utils

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("TAG", "Caught $throwable")
    }
    val scope = CoroutineScope(Job())

    scope.launch {
        launch(exceptionHandler) {
            throw RuntimeException()
        }
    }
}


private fun repeat(numberOfTimes: Int, block: () -> Unit) {
    repeat(numberOfTimes) {
        try {
            block()
        } catch (e: Exception) {
            Log.e("TAG", "Error: ${e.message}")
        }
    }
    block()
}

fun first() {
/*    viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        withTimeout(2000L) {
            doSomeStuff()
        }
        _uiState.update { it.copy(isLoading = false) }
    }*/
}

suspend fun doSomeStuff() {
    //does some stuff that takes some time
}

// retry with exponential backoff
// inspired by https://stackoverflow.com/questions/46872242/how-to-exponential-backoff-retry-on-kotlin-coroutines
private suspend fun <T> retry(
    times: Int,
    initialDelayMillis: Long = 100,
    maxDelayMillis: Long = 1000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelayMillis
    kotlin.repeat(times) {
        try {
            return block()
        } catch (exception: Exception) {
            Log.e("RETRY", exception.stackTraceToString())
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
    }
    return block() // last attempt
}