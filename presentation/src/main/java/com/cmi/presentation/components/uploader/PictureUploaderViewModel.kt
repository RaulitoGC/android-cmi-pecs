package com.cmi.presentation.components.uploader

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.cmi.presentation.R
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.config.add.model.PictureUploaderEvent
import com.cmi.presentation.config.add.model.PictureUploaderState
import com.cmi.presentation.ktx.toStringOrEmpty
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.utils.MessageBuilder
import com.cmi.presentation.utils.MessageType
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

abstract class PictureUploaderViewModel(
    contentType: PictureUploaderContentType,
    pictureModel: PictureModel,
    private val messageBuilder: MessageBuilder
): ViewModel() {

    val uiState = MutableStateFlow(
        PictureUploaderState(
            contentType = contentType,
            pictureModel = pictureModel
        )
    )

    fun handleEvent(event: PictureUploaderEvent) {
        when (event) {

            is PictureUploaderEvent.NameChanged -> {
                updatePictureName(event.pictureName)
            }

            is PictureUploaderEvent.ImageUriUpdated -> {
                updateImageUri(event.imageUri)
            }

            is PictureUploaderEvent.UploadPicture -> {
                uploadPicture(uiState.value.pictureModel)
            }

            is PictureUploaderEvent.MessageShown -> {
                uiState.value = uiState.value.copy(showMessage = null)
            }
        }
    }

    abstract fun uploadPicture(pictureModel: PictureModel?)

    protected fun updatePictureName(pictureName: String) {
        uiState.value = uiState.value.copy(pictureModel = uiState.value.pictureModel.copyName(pictureName))
    }

    protected fun updateImageUri(imageUri: Uri) {
        uiState.value = uiState.value.copy(pictureModel = uiState.value.pictureModel.copyPath(imageUri.toStringOrEmpty()))
    }

    protected fun showMessage(messageType: MessageType) {
        uiState.value = uiState.value.copy(showMessage = messageBuilder.build(messageType))
    }

    protected fun updateCategories(categories: List<CategorySelectableModel>) {
        uiState.value = uiState.value.copy(categories = categories)
    }

    protected fun cleanFields(){
        uiState.value = uiState.value.copy(pictureModel = uiState.value.pictureModel.reset())
    }

    companion object {
        @Composable
        fun create(contentType: PictureUploaderContentType): PictureUploaderViewModel {
            return when (contentType) {
                is PictureUploaderContentType.PictogramEditable, PictureUploaderContentType.PictogramEntry-> {
                    koinViewModel<PictogramPictureUploaderViewModel>{
                        parametersOf(contentType)
                    }
                }
                is PictureUploaderContentType.CategoryEditable, PictureUploaderContentType.CategoryEntry -> {
                    koinViewModel<CategoryPictureUploaderViewModel>{
                        parametersOf(contentType)
                    }
                }
            }
        }
    }

}
