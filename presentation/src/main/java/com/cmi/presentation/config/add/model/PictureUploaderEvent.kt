package com.cmi.presentation.config.add.model

import android.net.Uri

sealed class PictureUploaderEvent {

    class NameChanged(val pictureName: String): PictureUploaderEvent()

    class ImageUriUpdated(val imageUri: Uri): PictureUploaderEvent()

    data object UploadPicture : PictureUploaderEvent()

    data object MessageShown: PictureUploaderEvent()
}
