package com.cmi.presentation.components.uploader

import android.net.Uri
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.model.PictureModel

data class PictureUploaderState(
    val contentType: PictureUploaderContentType = PictureUploaderContentType.CategoryEntry,
    val pictureModel: PictureModel,
    val showMessage: String? = null,
    val categories: List<CategoryModel> = emptyList(),
    val cardViewConfig: CardViewConfig = CardViewConfig()
)

