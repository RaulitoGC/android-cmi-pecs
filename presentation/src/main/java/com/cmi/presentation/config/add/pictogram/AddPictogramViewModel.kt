package com.cmi.presentation.config.add.pictogram

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.AddPictogramUseCase
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.presentation.R
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.mapper.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class AddPictogramViewModel(
    private val itemsPerScreen: Int,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addPictogramUseCase: AddPictogramUseCase) : ViewModel() {

    private val _categories: MutableLiveData<Map<Int, List<CategorySelectableModel>>> by lazy {
        MutableLiveData<Map<Int, List<CategorySelectableModel>>>().also {
            it.value = emptyMap()
        }
    }
    val categories: LiveData<Map<Int, List<CategorySelectableModel>>> = _categories

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

    init {
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch {
        getCategoriesUseCase()
            .catch { exception ->
                Timber.e(exception)
                _showMessage.value = R.string.text_generic_error
            }
            .collect { list ->
                _categories.value = getCategoriesSelectableMapFormat(
                    itemsPerScreen = itemsPerScreen,
                    items = list.map {
                        it.toCategoryModel().toCategorySelectableModelUnChecked()
                    }
                )
            }
    }

    fun addPictogram(pictogramName: Editable?, pictureFileName: String?, categorySelected: CategorySelectableModel?) = viewModelScope.launch {
        if(isValidForm(pictogramName, pictureFileName, categorySelected)){
            val pictogramModel = PictogramModel(
                folder = categorySelected?.categoryModel?.name.orEmpty(),
                path = pictureFileName,
                name = pictogramName.toString(),
                priority = 0,
                isExternal = true,
                categoryId = categorySelected?.categoryModel?.categoryId,
                isSelected = true
            )

            addPictogramUseCase(pictogram = pictogramModel.toPictogram())
                .catch {
                    _showSuccess.value = false
                }.collect {
                    _showSuccess.value = true
                }
        }

    }

    private fun isValidForm(pictogramName: Editable?, pictureFileName: String?, categorySelected: CategorySelectableModel?): Boolean{

        if(categorySelected == null){
            _showMessage.value = R.string.text_configuration_error_empty_category
            return false
        }

        if(pictogramName.isNullOrEmpty()){
            _showMessage.value = R.string.text_configuration_error_empty_name
            return false
        }

        if(pictureFileName.isNullOrEmpty()){
            _showMessage.value = R.string.text_configuration_error_empty_image
            return false
        }

        return true
    }
}