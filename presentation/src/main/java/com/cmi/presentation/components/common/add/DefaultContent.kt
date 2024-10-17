package com.cmi.presentation.components.common.add

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.cmi.presentation.R

enum class ImageSourceContent(
    val permissionName: String,
    @DrawableRes val image: Int,
    @StringRes val contentDescription: Int,
    @StringRes val title: Int
) {
    CAMERA(
        permissionName = android.Manifest.permission.CAMERA,
        image = R.drawable.ic_camera,
        contentDescription = R.string.text_content_description_camera,
        title = R.string.text_camera
    ),
    GALLERY(
        permissionName = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        },
        image = R.drawable.ic_upload,
        contentDescription = R.string.text_content_description_upload,
        title = R.string.text_gallery
    )
}
