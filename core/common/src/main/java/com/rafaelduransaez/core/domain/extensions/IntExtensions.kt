package com.rafaelduransaez.core.domain.extensions

val Int.Companion.zero: Int
    get() = 0

val Int.Companion.negative
    get() = -1

fun Int.isPositive() = this > Int.zero
