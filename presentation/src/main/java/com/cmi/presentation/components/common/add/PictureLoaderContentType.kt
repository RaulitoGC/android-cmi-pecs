package com.cmi.presentation.components.common.add

import androidx.annotation.StringRes
import com.cmi.presentation.R

sealed class PictureLoaderContentType(
    @StringRes val title: Int,
    @StringRes val submitButtonText: Int
) {

    fun isSingleImage() = this is SingleImage
    fun isCategoryImage() = this is CategoryImage

    data object  SingleImage : PictureLoaderContentType(
        title = R.string.text_add_pictogram,
        submitButtonText = R.string.text_upload_pictogram
    )

    data object  CategoryImage : PictureLoaderContentType(
        title = R.string.text_add_category,
        submitButtonText = R.string.text_add_category
    )
}