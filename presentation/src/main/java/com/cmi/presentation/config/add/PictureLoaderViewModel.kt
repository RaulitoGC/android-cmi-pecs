package com.cmi.presentation.config.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.presentation.components.common.add.PictureLoaderContentType
import com.cmi.presentation.config.add.model.PictureLoaderEvent
import com.cmi.presentation.config.add.model.PictureLoaderState
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.model.mapper.getCategoriesSelectableMapFormat
import com.cmi.presentation.model.mapper.toCategoryModel
import com.cmi.presentation.model.mapper.toCategorySelectableModelUnChecked
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class PictureLoaderViewModel(
    contentType: PictureLoaderContentType,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    val uiState = MutableStateFlow(
        PictureLoaderState(
            contentType = contentType
        )
    )
    init {
        getCategories()
    }

    fun handleEvent(event: PictureLoaderEvent) {
        when (event) {

            is PictureLoaderEvent.NameChanged -> {
                updatePictureName(event.pictureName)
            }

            is PictureLoaderEvent.ImageUriUpdated -> {
                updateImageUri(event.imageUri)
            }
        }
    }

    private fun updatePictureName(pictureName: String) {
        uiState.value = uiState.value.copy(pictureName = pictureName)
    }

    private fun updateImageUri(imageUri: Uri) {
        uiState.value = uiState.value.copy(imageUri = imageUri)
    }

    private fun updateShowErrorMessage(showErrorMessage: Boolean) {
        uiState.value = uiState.value.copy(showErrorMessage = showErrorMessage)
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

}
