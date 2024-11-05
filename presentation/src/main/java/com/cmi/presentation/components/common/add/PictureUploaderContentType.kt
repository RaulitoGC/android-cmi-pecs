package com.cmi.presentation.components.common.add

import androidx.annotation.StringRes
import com.cmi.presentation.R

sealed class PictureUploaderContentType(
    @StringRes val title: Int,
    @StringRes val subTitle: Int,
    @StringRes val submitButtonText: Int
) {

    fun showCategoriesCarousel(): Boolean = this is PictogramEntry

    data class  PictogramEditable(
        override val pictureId: Int
    ) : PictureUploaderContentType(
        title = R.string.text_update_pictogram,
        subTitle = R.string.text_pictogram_data,
        submitButtonText = R.string.text_update_pictogram,
    ), PictureEditable

    data object  PictogramEntry: PictureUploaderContentType(
        title = R.string.text_add_pictogram,
        subTitle = R.string.text_upload_pictogram,
        submitButtonText = R.string.text_upload_pictogram,
    )

    data class  CategoryEditable(
        override val pictureId: Int
    ) : PictureUploaderContentType(
        title = R.string.text_update_category,
        subTitle = R.string.text_category_data,
        submitButtonText = R.string.text_update_category
    ), PictureEditable

    data object  CategoryEntry: PictureUploaderContentType(
        title = R.string.text_add_category,
        subTitle = R.string.text_upload_category,
        submitButtonText = R.string.text_add_category
    )
}

interface PictureEditable {
    val pictureId: Int
}