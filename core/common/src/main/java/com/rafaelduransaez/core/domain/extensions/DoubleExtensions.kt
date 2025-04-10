package com.rafaelduransaez.core.domain.extensions

val Double.Companion.zero: Double
    get() = 0.0

fun Double.isNotZero(): Boolean = this != 0.0