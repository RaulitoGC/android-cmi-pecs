package com.cmi.presentation.config.add.model

import android.Manifest
import android.media.Image
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.cmi.presentation.R

data class PictureLoaderState(
    val isLoading: Boolean = false,
    val pictureName: String = "",
    val contentType: PictureLoaderContentType = PictureLoaderContentType.SingleImage
)

sealed class ImageSourcePermission(
    open val permissionName: String,
    open val isPermissionRequested: Boolean
){
    data class Camera(
        override val permissionName: String = Manifest.permission.CAMERA,
        override val isPermissionRequested: Boolean = false
    ): ImageSourcePermission(permissionName, isPermissionRequested)

    data class Gallery(
        override val permissionName: String = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        },
        override val isPermissionRequested: Boolean = false
    ): ImageSourcePermission(permissionName, isPermissionRequested)
}
