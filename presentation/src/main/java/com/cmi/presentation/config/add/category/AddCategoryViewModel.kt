package com.cmi.presentation.config.add.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.AddCategoryUseCase
import com.cmi.presentation.R
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.mapper.toCategory
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddCategoryViewModel @Inject constructor(private val addCategoryUseCase: AddCategoryUseCase) : ViewModel() {

    private val _showMessage: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val showMessage: LiveData<Int> = _showMessage

    private val _showSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val showSuccess: LiveData<Boolean> = _showSuccess

    private var takePicturePath : String? = null
    fun getTakePicturePath() = takePicturePath.orEmpty()
    fun setTakePicturePath(path: String){
        this.takePicturePath = path
    }

    fun addCategory(pictogramName: String?, pictureFileName: String?) = viewModelScope.launch {
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