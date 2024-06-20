package com.cmi.presentation.config.add.model

import android.net.Uri

sealed class PictureLoaderEvent {

//    object ToggleAuthenticationMode: AuthenticationEvent()

    class NameChanged(val pictureName: String): PictureLoaderEvent()

    class ImageUriUpdated(val imageUri: Uri): PictureLoaderEvent()

//    class PasswordChanged(val password: String): AuthenticationEvent()
//
//    object Authenticate: AuthenticationEvent()
//
//    object ErrorDismissed: AuthenticationEvent()
}
