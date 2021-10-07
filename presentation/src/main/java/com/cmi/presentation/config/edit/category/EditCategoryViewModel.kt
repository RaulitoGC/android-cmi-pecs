package com.cmi.presentation.config.edit.category

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.UpdateCategoryUseCase
import com.cmi.presentation.R
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.mapper.toCategory
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class EditCategoryViewModel @Inject constructor(private val updateCategoryUseCase: UpdateCategoryUseCase) :
    ViewModel() {

    private val _uiState: MutableLiveData<EditCategoryUiState> by lazy {
        MutableLiveData<EditCategoryUiState>()
    }
    val uiState: LiveData<EditCategoryUiState> = _uiState

    private var takePicturePath : String? = null
    fun getTakePicturePath() = takePicturePath.orEmpty()
    fun setTakePicturePath(path: String){
        this.takePicturePath = path
    }

    fun updateCategory(name: Editable?, imagePath: String?, isExternal: Boolean, categoryModel: CategoryModel) =
        viewModelScope.launch {

            if (isValidForm(name = name, imagePath = imagePath)) {
                val categoryModelToUpdate = CategoryModel(
                    categoryId = categoryModel.categoryId,
                    folder = categoryModel.folder,
                    path = imagePath.toString(),
                    name = name.toString(),
                    priority = categoryModel.priority,
                    isExternal = isExternal,
                    isSelected = categoryModel.isSelected
                )
                updateCategoryUseCase(category = categoryModelToUpdate.toCategory())
                    .catch { exception ->
                        Timber.e(exception)
                        emitUiState(showMessage = R.string.text_generic_error)
                    }.collect {
                        emitUiState(showSuccess = Any())
                    }
            }
        }

    private fun isValidForm(name: Editable?, imagePath: String?): Boolean {

        if (name.isNullOrEmpty()) {
            emitUiState(showMessage = R.string.text_configuration_error_empty_name)
            return false
        }

        if (imagePath.isNullOrEmpty()) {
            emitUiState(showMessage = R.string.text_configuration_error_empty_image)
            return false
        }

        return true
    }

    private fun emitUiState(showSuccess: Any? = null, showMessage: Int? = null) {
        _uiState.value = EditCategoryUiState(
            showSuccess = showSuccess,
            showMessage = showMessage
        )
    }
}

data class EditCategoryUiState(
    val showSuccess: Any? = null,
    val showMessage: Int? = null
)