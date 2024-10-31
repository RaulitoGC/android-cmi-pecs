package com.cmi.presentation.components.selecter

import com.cmi.presentation.model.PictureModel

sealed class PictureSelecterForPecsEvent {
    data class PictureSelected(
        val pictureModel: PictureModel
    ) : PictureSelecterForPecsEvent()

    data object UpdatePictures : PictureSelecterForPecsEvent()

    data object SuccessMessageShown : PictureSelecterForPecsEvent()
    data object ErrorMessageShown : PictureSelecterForPecsEvent()
}
