package com.cmi.presentation.ktx

val Boolean.isTrue
    get() = this
val Boolean.isFalse
    get() = !this

val Boolean?.orFalse
    get() = this ?: false

val Boolean?.orTrue
    get() = this ?: true