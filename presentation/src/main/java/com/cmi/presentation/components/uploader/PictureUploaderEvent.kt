package com.cmi.presentation.components.uploader

import android.net.Uri
import com.cmi.presentation.model.CategoryModel

sealed class PictureUploaderEvent {

    class NameChanged(val pictureName: String): PictureUploaderEvent()

    class ImageUriUpdated(val imageUri: Uri): PictureUploaderEvent()

    data class CategorySelected(val categoryModel: CategoryModel): PictureUploaderEvent()

    data object UploadPicture : PictureUploaderEvent()

    data object MessageShown: PictureUploaderEvent()
}
