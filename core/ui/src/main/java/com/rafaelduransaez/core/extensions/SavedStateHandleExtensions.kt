package com.rafaelduransaez.core.extensions

import androidx.lifecycle.SavedStateHandle

inline fun <reified T> SavedStateHandle.getAndRemove(key: String, defaultValue: T): T {
    return this.get<T>(key)?.also { this.remove<T>(key) } ?: defaultValue
}