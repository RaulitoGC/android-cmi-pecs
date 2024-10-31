package com.cmi.presentation.ktx

val Boolean?.isTrue
    get() = this == true
val Boolean?.isFalse
    get() = this == false

val Boolean?.orFalse
    get() = this ?: false

val Boolean?.orTrue
    get() = this ?: true