package com.cmi.presentation.components.uploader

import android.net.Uri
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.model.PictureModel

data class PictureUploaderState(
    val contentType: PictureUploaderContentType = PictureUploaderContentType.CategoryEntry,
    val pictureModel: PictureModel,
    val showMessage: String? = null,
    val uriImage: Uri? = null,
    val categories: List<CategorySelectableModel> = emptyList()
)

