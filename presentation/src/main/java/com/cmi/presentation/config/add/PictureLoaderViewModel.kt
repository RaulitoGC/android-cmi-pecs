package com.cmi.presentation.config.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.AddCategoryUseCase
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.presentation.R
import com.cmi.presentation.components.common.add.PictureLoaderContentType
import com.cmi.presentation.config.add.model.PictureLoaderEvent
import com.cmi.presentation.config.add.model.PictureLoaderState
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.model.mapper.getCategoriesSelectableMapFormat
import com.cmi.presentation.model.mapper.toCategory
import com.cmi.presentation.model.mapper.toCategoryModel
import com.cmi.presentation.model.mapper.toCategorySelectableModelUnChecked
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class PictureLoaderViewModel(
    contentType: PictureLoaderContentType,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase
): ViewModel() {

    val uiState = MutableStateFlow(
        PictureLoaderState(
            contentType = contentType
        )
    )
    init {
        if(contentType == PictureLoaderContentType.SingleImage) {
            getCategories()
        }
    }

    fun handleEvent(event: PictureLoaderEvent) {
        when (event) {

            is PictureLoaderEvent.NameChanged -> {
                updatePictureName(event.pictureName)
            }

            is PictureLoaderEvent.ImageUriUpdated -> {
                updateImageUri(event.imageUri)
            }

            is PictureLoaderEvent.UploadCategory -> {
                addCategory(uiState.value.pictureName, uiState.value.imageUri.toString())
            }
        }
    }

    private fun updatePictureName(pictureName: String) {
        uiState.value = uiState.value.copy(pictureName = pictureName)
    }

    private fun updateImageUri(imageUri: Uri) {
        uiState.value = uiState.value.copy(imageUri = imageUri)
    }

    private fun showMessage(message: String) {
        uiState.value = uiState.value.copy(showMessage = message)
    }

    private fun updateCategories(categories: List<CategorySelectableModel>) {
        uiState.value = uiState.value.copy(categories = categories)
    }

    private fun getCategories() = viewModelScope.launch {
        getCategoriesUseCase()
            .catch { exception ->
                Timber.e(exception)
                updateShowErrorMessage(true)
            }
            .collect { list ->
                updateShowErrorMessage(false)
                val categories = getCategoriesSelectableMapFormat(
                    itemsPerScreen = 3,
                    items = list.map {
                        it.toCategoryModel().toCategorySelectableModelUnChecked()
                    }
                )
                updateCategories(categories.flatMap { it.value })
            }
    }

    private fun addCategory(pictogramName: String?, pictureFileName: String?) = viewModelScope.launch {
        if (isValidForm(pictogramName, pictureFileName)) {
            val categoryModel = CategoryModel(
                folder = pictogramName?.replace("\\s".toRegex(), ""),
                path = pictureFileName,
                name = pictogramName,
                priority = 0,
                isExternal = true,
                isSelected = true
            )
            addCategoryUseCase(category = categoryModel.toCategory())
                .catch {
                    _showSuccess.value = false
                }.collect {
                    _showSuccess.value = true
                }
        }
    }

    private fun isValidForm(pictogramName: String?, pictureFileName: String?): Boolean {
        if (pictogramName.isNullOrEmpty()) {
            _showMessage.value = R.string.text_configuration_error_empty_name
            return false
        }

        if (pictureFileName.isNullOrEmpty()) {
            _showMessage.value = R.string.text_configuration_error_empty_image
            return false
        }

        return true
    }

}
