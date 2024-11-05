package com.cmi.presentation.components.remover

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.cmi.presentation.R
import com.cmi.presentation.components.remover.type.CategoryRemoverForPecsViewmodel
import com.cmi.presentation.components.remover.type.PictogramRemoverForPecsViewModel
import com.cmi.presentation.components.remover.type.PictureRemoverContentType
import com.cmi.presentation.config.add.model.SelectableTitleConfig
import com.cmi.presentation.ktx.orFalse
import com.cmi.presentation.manager.StringResourceManager
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.utils.MessageBuilder
import com.cmi.presentation.utils.MessageType
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

abstract class PictureRemoverForPecsViewModel(
    private val messageBuilder: MessageBuilder,
    private val stringResourceManager: StringResourceManager
) : ViewModel() {

    val uiState = MutableStateFlow(
        PictureRemoverState()
    )

    fun handleEvent(event: PictureRemoverEvent) {
        when (event) {
            is PictureRemoverEvent.ShowAlertMessage -> {
                showAlertMessage(event.messageType)
            }

            is PictureRemoverEvent.ToastMessageShown -> {
                uiState.value = uiState.value.copy(showToastMessage = null)
            }

            is PictureRemoverEvent.AlertMessageShown -> {
                uiState.value = uiState.value.copy(showAlertMessage = null)
            }

            is PictureRemoverEvent.PictureSelectedForRemoval -> {
                pictureSelectedForRemoval(event.pictureModel)
            }

            is PictureRemoverEvent.RemovePictures -> {
                removePictures(
                    picturesSelected = uiState.value.pictureModels.filter {
                        it.isSelected.orFalse
                    }
                )
            }
        }
    }

    abstract fun removePictures(picturesSelected: List<PictureModel>)
    abstract fun getPrincipalTitle(): String
    abstract fun getDescription(): String

    private fun pictureSelectedForRemoval(pictureModel: PictureModel) {
        val pictureModels = uiState.value.pictureModels
        val pictureModelUpdated=  pictureModels.map {
            if (it.id == pictureModel.id) {
                it.copyIsSelected(isSelected = it.isSelected.orFalse.not())
            } else {
                it
            }
        }
        uiState.value = uiState.value.copy(
            pictureModels = pictureModelUpdated,
            titleConfig = getTitleConfig(pictureModelUpdated)
        )
    }

    private fun showAlertMessage(messageType: MessageType) {
        uiState.value = uiState.value.copy(showAlertMessage = messageBuilder.build(messageType))
    }

    protected fun showToastMessage(messageType: MessageType) {
        uiState.value = uiState.value.copy(showToastMessage = messageBuilder.build(messageType))
    }

    protected fun showPictures(pictureModels: List<PictureModel>) {
        if(pictureModels.isEmpty()){
            uiState.value = uiState.value.copy(showAlertMessage = stringResourceManager.getString(R.string.text_message_empty_categories))
        } else {
            uiState.value = uiState.value.copy(
                pictureModels = pictureModels,
                titleConfig = getTitleConfig(pictureModels)
            )
        }
    }

    protected fun updateDescription(description: String) {
        uiState.value = uiState.value.copy(description = description)
    }

    private fun getTitleConfig(pictureModels: List<PictureModel>): SelectableTitleConfig {
        val isEnabled = pictureModels.any { it.isSelected.orFalse }
        val titleBuilder = StringBuilder().apply {
            append(getPrincipalTitle())
            if (isEnabled) {
                val itemsSelected = pictureModels.filter { it.isSelected.orFalse }.size
                append(" ${
                    stringResourceManager.getString(
                        R.string.text_select_picture_size_format,
                        itemsSelected
                    )
                }")
            }
        }
        return SelectableTitleConfig(
            title = titleBuilder.toString(),
            isActionEnabled = isEnabled
        )
    }

    protected fun showLoading(isLoading: Boolean) {
        uiState.value = uiState.value.copy(isLoading = isLoading)
    }

    companion object {
        @Composable
        fun create(contentType: PictureRemoverContentType): PictureRemoverForPecsViewModel {
            return when (contentType) {
                is PictureRemoverContentType.Category -> {
                    koinViewModel<CategoryRemoverForPecsViewmodel>()
                }

                is PictureRemoverContentType.Pictogram -> {
                    koinViewModel<PictogramRemoverForPecsViewModel> {
                        parametersOf(contentType.categoryId)
                    }
                }
            }
        }
    }
}