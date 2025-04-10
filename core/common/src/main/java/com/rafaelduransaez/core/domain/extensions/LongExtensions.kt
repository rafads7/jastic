package com.rafaelduransaez.core.domain.extensions

val Long.Companion.zero: Long
    get() = 0L

fun Long.isPositive() = this > Long.zero