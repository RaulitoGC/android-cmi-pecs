package com.cmi.presentation.config.delete.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.DeleteCategoriesUseCase
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.presentation.Constants
import com.cmi.presentation.config.select.category.SelectCategoryUiState
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.model.mapper.toCategory
import com.cmi.presentation.model.mapper.toCategoryModel
import com.cmi.presentation.model.mapper.toCategorySelectableModelUnChecked
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class DeleteCategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val deleteCategoriesUseCase: DeleteCategoriesUseCase
) : ViewModel() {

    private val _categories: MutableLiveData<List<CategorySelectableModel>> by lazy {
        MutableLiveData<List<CategorySelectableModel>>()
    }
    val categories: LiveData<List<CategorySelectableModel>> = _categories

    private val _uiState: MutableLiveData<SelectCategoryUiState> by lazy {
        MutableLiveData<SelectCategoryUiState>()
    }
    val uiState: LiveData<SelectCategoryUiState> = _uiState

    fun getExternalCategories() = viewModelScope.launch {
        delay(Constants.SHIMMER_EFFECT_DELAY) //Delay for show shimmer effect
        getCategoriesUseCase()
            .catch { throwable ->
                Timber.e(throwable)
                emitUiState(showError = Any())
            }.collect { list ->
                _categories.value = list.filter { it.isExternal == true }
                    .map { it.toCategoryModel().toCategorySelectableModelUnChecked() }
            }
    }

    fun deleteCategories(categoriesSelected: List<CategorySelectableModel>) =
        viewModelScope.launch {
            if (categoriesSelected.isNotEmpty()) {
                val categories = categoriesSelected.map { it.toCategoryModel().toCategory() }
                deleteCategoriesUseCase(categories = categories)
                    .catch { throwable ->
                        Timber.e(throwable)
                        emitUiState(showError = Any())
                    }.collect {
                        emitUiState(showSuccess = Any())
                    }
            }
        }

    private fun emitUiState(showSuccess: Any? = null, showError: Any? = null) {
        _uiState.value = SelectCategoryUiState(
            showSuccess = showSuccess,
            showError = showError
        )
    }
}

data class DeleteCategoryUiState(
    val showSuccess: Any? = null,
    val showError: Any? = null
)