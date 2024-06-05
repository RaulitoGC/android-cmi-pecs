package com.cmi.presentation.config.add

import androidx.lifecycle.ViewModel
import com.cmi.presentation.config.add.model.ImageSourcePermission
import com.cmi.presentation.config.add.model.PictureLoaderEvent
import com.cmi.presentation.config.add.model.PictureLoaderState
import kotlinx.coroutines.flow.MutableStateFlow

class PictureLoaderViewModel: ViewModel() {

    val uiState = MutableStateFlow(PictureLoaderState())

    fun handleEvent(authenticationEvent: PictureLoaderEvent) {
        when (authenticationEvent) {

            is PictureLoaderEvent.NameChanged -> {
                updatePictureName(authenticationEvent.pictureName)
            }


//            is PictureLoaderEvent.ImageSourceChanged -> {
//                updateImageSource(authenticationEvent.imageSource)
//            }

        }
    }

    private fun updatePictureName(pictureName: String) {
        uiState.value = uiState.value.copy(pictureName = pictureName)
    }

//    private fun updateImageSource(imageSource: ImageSource) {
//        uiState.value = uiState.value.copy(imageSource = imageSource)
//    }
}
