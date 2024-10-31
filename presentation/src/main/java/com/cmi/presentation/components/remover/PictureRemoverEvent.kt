package com.cmi.presentation.components.remover

import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.utils.MessageType

sealed class PictureRemoverEvent {
    data class ShowAlertMessage(val messageType: MessageType): PictureRemoverEvent()
    data object RemovePictures: PictureRemoverEvent()
    data class PictureSelectedForRemoval(val pictureModel: PictureModel): PictureRemoverEvent()

    data object ToastMessageShown: PictureRemoverEvent()
    data object AlertMessageShown: PictureRemoverEvent()
}