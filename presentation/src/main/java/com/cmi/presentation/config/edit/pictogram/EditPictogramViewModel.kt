package com.cmi.presentation.config.edit.pictogram

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.UpdatePictogramUseCase
import com.cmi.presentation.R
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.mapper.toPictogram
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class EditPictogramViewModel @Inject constructor(private val updatePictogramUseCase: UpdatePictogramUseCase) :
    ViewModel() {

    private val _uiState: MutableLiveData<EditPictogramUiState> by lazy {
        MutableLiveData<EditPictogramUiState>()
    }
    val uiState: LiveData<EditPictogramUiState> = _uiState

    private var takePicturePath : String? = null
    fun getTakePicturePath() = takePicturePath.orEmpty()
    fun setTakePicturePath(path: String){
        this.takePicturePath = path
    }

    fun updatePictogram(name: Editable?, imagePath: String?, isExternal: Boolean, pictogramModel: PictogramModel) =
        viewModelScope.launch {
            if (isValidForm(name = name, imagePath = imagePath)) {
                val pictogramModelToUpdate = PictogramModel(
                    pictogramId = pictogramModel.pictogramId,
                    folder = pictogramModel.folder,
                    path = imagePath.toString(),
                    name = name.toString(),
                    priority = pictogramModel.priority,
                    isExternal = isExternal,
                    categoryId = pictogramModel.categoryId,
                    isSelected = pictogramModel.isSelected
                )
                updatePictogramUseCase(pictogram = pictogramModelToUpdate.toPictogram())
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
        _uiState.value = EditPictogramUiState(
            showSuccess = showSuccess,
            showMessage = showMessage
        )
    }
}

data class EditPictogramUiState(
    val showSuccess: Any? = null,
    val showMessage: Int? = null
)