package com.cmi.presentation.config.select.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.domain.usecase.UpdateCategoriesUseCase
import com.cmi.presentation.Constants
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.model.mapper.toCategory
import com.cmi.presentation.model.mapper.toCategoryModel
import com.cmi.presentation.model.mapper.toCategorySelectableModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SelectCategoryForPecsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase
) : ViewModel() {

    private val _categories: MutableLiveData<List<CategorySelectableModel>> by lazy {
        MutableLiveData<List<CategorySelectableModel>>()
    }
    val categories: LiveData<List<CategorySelectableModel>> = _categories

    private val _uiState: MutableLiveData<SelectCategoryUiState> by lazy {
        MutableLiveData<SelectCategoryUiState>()
    }
    val uiState: LiveData<SelectCategoryUiState> = _uiState

    init {
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch {
        delay(Constants.SHIMMER_EFFECT_DELAY) //Delay for show shimmer effect
        getCategoriesUseCase()
            .catch { throwable ->
                Timber.e(throwable)
                emitUiState(showError = Any())
            }.collect { list ->
                _categories.value = list.map { it.toCategoryModel().toCategorySelectableModel() }
            }
    }

    fun updateCategoriesForPecs(categoriesSelected: List<CategorySelectableModel>) =
        viewModelScope.launch {
            if (categoriesSelected.isNotEmpty()) {
                val categories = categoriesSelected.map { it.toCategoryModel().toCategory() }
                updateCategoriesUseCase(categories = categories)
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

data class SelectCategoryUiState(
    val showSuccess: Any? = null,
    val showError: Any? = null
)