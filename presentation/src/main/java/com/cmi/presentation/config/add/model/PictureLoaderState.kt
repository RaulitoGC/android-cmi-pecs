package com.cmi.presentation.config.add.model

import android.Manifest
import android.net.Uri
import com.cmi.presentation.components.common.add.PictureLoaderContentType
import com.cmi.presentation.model.CategorySelectableModel

data class PictureLoaderState(
    val contentType: PictureLoaderContentType = PictureLoaderContentType.SingleImage,
    val pictureName: String = "",
    val imageUri: Uri? = null,
    val showErrorMessage: Boolean = false,
    val categories: List<CategorySelectableModel> = emptyList()
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
