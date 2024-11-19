package com.cmi.presentation.components.selecter

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.cmi.presentation.Constants.ACTION_CATEGORY_ID
import com.cmi.presentation.Constants.ATTRIBUTE_CATEGORY_ID
import com.cmi.presentation.R
import com.cmi.presentation.components.remover.type.PictogramRemoverForPecsViewModel
import com.cmi.presentation.components.selecter.type.CategorySelecterForPecsViewModel
import com.cmi.presentation.components.selecter.type.PictogramSelecterForPecsViewModel
import com.cmi.presentation.components.selecter.type.PictureSelecterContentType
import com.cmi.presentation.config.add.model.SelectableTitleConfig
import com.cmi.presentation.ktx.orFalse
import com.cmi.presentation.manager.StringResourceManager
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.model.isAction
import com.cmi.presentation.model.isAttribute
import com.cmi.presentation.model.isCategory
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
                    pictureModels = uiState.value.pictureModels.map {
                        it.copySelectedForPecs(it.isSelected.orFalse)
                    }
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

    protected fun showErrorMessage(
        @StringRes message: Int = R.string.text_generic_error
    ) {
        uiState.value = uiState.value.copy(showErrorToastMessage = stringResourceManager.getString(message))
    }

    private fun onPictureSelected(pictureModel: PictureModel){

        if(pictureModel.isCategory() && ((pictureModel as CategoryModel).isAttribute() || pictureModel.isAction())){
            showErrorMessage(
                message = R.string.text_action_nor_attribute_unselected
            )
            return
        }

        val currentPictures = uiState.value.pictureModels
        val updatedPictures = currentPictures.map {
            if (it.id == pictureModel.id) {
                val isSelectedForPecs = it.isSelected.orFalse
                it.copyIsSelected(isSelected = isSelectedForPecs.not())
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
