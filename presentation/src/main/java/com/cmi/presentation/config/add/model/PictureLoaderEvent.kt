package com.cmi.presentation.config.add.model

sealed class PictureLoaderEvent {

//    object ToggleAuthenticationMode: AuthenticationEvent()

    class NameChanged(val pictureName: String): PictureLoaderEvent()

//    class PasswordChanged(val password: String): AuthenticationEvent()
//
//    object Authenticate: AuthenticationEvent()
//
//    object ErrorDismissed: AuthenticationEvent()
}
