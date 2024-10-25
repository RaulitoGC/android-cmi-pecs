package com.cmi.presentation.ktx

val Int?.orZero
    get() = this ?: 0

val Int?.orNegative
    get() = this ?: -1