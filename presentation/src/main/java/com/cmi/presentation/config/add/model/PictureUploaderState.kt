package com.cmi.presentation.config.add.model

import android.net.Uri
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.PictureModel

data class PictureUploaderState(
    val contentType: PictureUploaderContentType = PictureUploaderContentType.CategoryEntry,
    val pictureModel: PictureModel,
    val showMessage: String? = null,
    val categories: List<CategorySelectableModel> = emptyList()
)

