package com.cmi.presentation.components.common.add

import androidx.annotation.StringRes
import com.cmi.presentation.R

sealed class PictureUploaderContentType(
    @StringRes val title: Int,
    @StringRes val submitButtonText: Int
) {

    fun showCategoriesCarousel(): Boolean = this is PictogramEditable || this is PictogramEntry

    data class  PictogramEditable(
        override val pictureId: Int
    ) : PictureUploaderContentType(
        title = R.string.text_add_pictogram,
        submitButtonText = R.string.text_upload_pictogram,
    ), PictureEditable

    data object  PictogramEntry: PictureUploaderContentType(
        title = R.string.text_add_pictogram,
        submitButtonText = R.string.text_upload_pictogram,
    )

    data class  CategoryEditable(
        override val pictureId: Int
    ) : PictureUploaderContentType(
        title = R.string.text_add_category,
        submitButtonText = R.string.text_add_category
    ), PictureEditable

    data object  CategoryEntry: PictureUploaderContentType(
        title = R.string.text_add_category,
        submitButtonText = R.string.text_add_category
    )
}

interface PictureEditable {
    val pictureId: Int
}