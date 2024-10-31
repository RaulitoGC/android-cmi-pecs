package com.cmi.presentation.components.uploader

import android.net.Uri

sealed class PictureUploaderEvent {

    class NameChanged(val pictureName: String): PictureUploaderEvent()

    class ImageUriUpdated(val imageUri: Uri): PictureUploaderEvent()

    data object UploadPicture : PictureUploaderEvent()

    data object MessageShown: PictureUploaderEvent()
}
