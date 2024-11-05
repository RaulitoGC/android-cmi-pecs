package com.cmi.presentation.components.selecter

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.cmi.presentation.R
import com.cmi.presentation.components.remover.type.PictogramRemoverForPecsViewModel
import com.cmi.presentation.components.selecter.type.CategorySelecterForPecsViewModel
import com.cmi.presentation.components.selecter.type.PictogramSelecterForPecsViewModel
import com.cmi.presentation.components.selecter.type.PictureSelecterContentType
import com.cmi.presentation.config.add.model.SelectableTitleConfig
import com.cmi.presentation.ktx.orFalse
import com.cmi.presentation.manager.StringResourceManager
import com.cmi.presentation.model.PictureModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

abstract class PictureSelecterForPecsViewModel(
    private val stringResourceManager: StringResourceManager
) : ViewModel() {

    private val initialTitle = stringResourceManager.getString(R.string.text_select_category_for_pecs)

    val uiState = MutableStateFlow(
        PictureSelecterForPecsState(
            titleConfig = SelectableTitleConfig(
                title = initialTitle,
                isActionEnabled = false
            )
        )
    )

    fun handleEvent(event: PictureSelecterForPecsEvent) {
        when (event) {
            is PictureSelecterForPecsEvent.PictureSelected -> {
                onPictureSelected(
                    pictureModel = event.pictureModel
                )
            }

            is PictureSelecterForPecsEvent.UpdatePictures -> {
                onUpdatePicturesSelected(
                    pictureModels = uiState.value.pictureModels.filter { it.isSelectedForPecs.orFalse }
                )
            }

            PictureSelecterForPecsEvent.ErrorMessageShown -> {
                uiState.value = uiState.value.copy(showErrorToastMessage = null)
            }
            PictureSelecterForPecsEvent.SuccessMessageShown -> {
                uiState.value = uiState.value.copy(showSuccessToastMessage = null)
            }
        }
    }


    protected fun showLoading(isLoading: Boolean) {
        uiState.value = uiState.value.copy(isLoading = isLoading)
    }

    protected fun showPictures(pictureModels: List<PictureModel>) {
        uiState.value = uiState.value.copy(
            pictureModels = pictureModels,
            titleConfig = getTitleConfig(pictureModels)
        )
    }

    protected fun showSuccessMessage() {
        uiState.value = uiState.value.copy(showSuccessToastMessage = getSuccessMessage())
    }

    protected fun showErrorMessage() {
        uiState.value = uiState.value.copy(showErrorToastMessage = stringResourceManager.getString(R.string.text_generic_error))
    }

    private fun onPictureSelected(pictureModel: PictureModel){
        val currentPictures = uiState.value.pictureModels
        val updatedPictures = currentPictures.map {
            if (it.id == pictureModel.id) {
                val isSelectedForPecs = it.isSelectedForPecs.orFalse
                it.copyIsSelected(isSelected = isSelectedForPecs.not()).copySelectedForPecs(isSelected = isSelectedForPecs.not())
            } else {
                it
            }
        }
        uiState.value = uiState.value.copy(
            pictureModels = updatedPictures,
            titleConfig = getTitleConfig(updatedPictures)
        )
    }

    abstract fun getSuccessMessage(): String

    abstract fun onUpdatePicturesSelected(pictureModels: List<PictureModel>)

    private fun getTitleConfig(pictureModels: List<PictureModel>): SelectableTitleConfig {
        val isEnabled = pictureModels.any { it.isSelectedForPecs.orFalse }
        val titleBuilder = StringBuilder().apply {
            append(stringResourceManager.getString(R.string.text_select_category_for_pecs))
            if (isEnabled) {
                val itemsSelected = pictureModels.filter { it.isSelectedForPecs.orFalse }.size
                append(" ${
                    stringResourceManager.getString(
                        R.string.text_select_category_size_format,
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

    companion object {
        @Composable
        fun create(contentType: PictureSelecterContentType): PictureSelecterForPecsViewModel {
            return when (contentType) {
                is PictureSelecterContentType.Category -> {
                    koinViewModel<CategorySelecterForPecsViewModel>{
                        parametersOf(contentType)
                    }
                }

                is PictureSelecterContentType.Pictogram -> {
                    koinViewModel<PictogramSelecterForPecsViewModel> {
                        parametersOf(contentType.categoryId)
                    }
                }
            }
        }
    }
}
