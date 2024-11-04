package com.cmi.presentation.components.uploader

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.components.uploader.type.CategoryPictureUploaderViewModel
import com.cmi.presentation.components.uploader.type.PictogramPictureUploaderViewModel
import com.cmi.presentation.ktx.toStringOrEmpty
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.utils.MessageBuilder
import com.cmi.presentation.utils.MessageType
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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

    open fun handleEvent(event: PictureUploaderEvent) {
        when (event) {

            is PictureUploaderEvent.NameChanged -> {
                updatePictureName(event.pictureName)
            }

            is PictureUploaderEvent.ImageUriUpdated -> {
                updateIsExternal(isExternal = true)
                updateImageUri(event.imageUri)
            }

            is PictureUploaderEvent.UploadPicture -> {
                uploadPicture(uiState.value.pictureModel)
            }

            is PictureUploaderEvent.MessageShown -> {
                uiState.value = uiState.value.copy(showMessage = null)
            }

            else -> {
                throw IllegalArgumentException("Event $event is not supported")
            }
        }
    }

    // region Abstract Methods
    abstract fun uploadPicture(pictureModel: PictureModel?)
    // endregion

    // region Private Methods
    private fun updatePictureName(pictureName: String) {
        uiState.value = uiState.value.copy(pictureModel = uiState.value.pictureModel.copyName(pictureName))
    }

    private fun updateImageUri(imageUri: Uri) {
        uiState.value = uiState.value.copy(
            pictureModel = uiState.value.pictureModel.copyPath(imageUri.toString())
        )
    }

    private fun updateIsExternal(isExternal: Boolean) {
        uiState.value = uiState.value.copy(pictureModel = uiState.value.pictureModel.copyIsExternal(isExternal))
    }

    // endregion

    // region Protected Methods
    protected fun updatePictureModel(pictureModel: PictureModel){
        uiState.value = uiState.value.copy(pictureModel = pictureModel)
    }

    protected fun showMessage(messageType: MessageType) {
        uiState.value = uiState.value.copy(showMessage = messageBuilder.build(messageType))
    }

    protected fun updateCategories(categories: List<CategoryModel>) {
        uiState.value = uiState.value.copy(categories = categories)
    }

    protected fun cleanFields(){
        uiState.value = uiState.value.copy(pictureModel = uiState.value.pictureModel.reset())
    }
    // endregion

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
