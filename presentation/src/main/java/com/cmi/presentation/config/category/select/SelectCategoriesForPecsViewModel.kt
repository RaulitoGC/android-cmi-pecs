package com.cmi.presentation.config.category.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.domain.usecase.UpdateCategoriesUseCase
import com.cmi.presentation.Constants.SHIMMER_EFFECT_DELAY
import com.cmi.presentation.R
import com.cmi.presentation.config.add.model.SelectableTitleConfig
import com.cmi.presentation.ktx.orFalse
import com.cmi.presentation.manager.StringResourceManager
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.mapper.toCategory
import com.cmi.presentation.model.mapper.toCategoryModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class SelectCategoriesForPecsViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
    private val stringResourceManager: StringResourceManager
) : ViewModel() {

    private val initialTitle = stringResourceManager.getString(R.string.text_select_category_for_pecs)

    val uiState = MutableStateFlow(
        SelectCategoriesForPecsState(
            titleConfig = SelectableTitleConfig(
                title = initialTitle,
                isActionEnabled = false
            )
        )
    )

    init {
        getCategories()
    }

    fun handleEvent(selectCategoriesForPecsEvent: SelectCategoriesForPecsEvent) {
        when (selectCategoriesForPecsEvent) {
            is SelectCategoriesForPecsEvent.CategorySelection -> {
                updateCategory(
                    categoryId = selectCategoriesForPecsEvent.categoryId,
                    isSelected = selectCategoriesForPecsEvent.isSelected
                )
            }

            is SelectCategoriesForPecsEvent.UpdateCategories -> {
                updateCategoriesForPecs(
                    categoriesModel = uiState.value.categories
                )
            }

            SelectCategoriesForPecsEvent.ErrorMessageShown -> {
                uiState.value = uiState.value.copy(showError = null)
            }
            SelectCategoriesForPecsEvent.SuccessMessageShown -> {
                uiState.value = uiState.value.copy(showSuccess = null)
            }
        }
    }

    private fun getCategories() = viewModelScope.launch {
        showLoading(isLoading = true)
        val timeForDelay = uiState.value.categories.size
        if (timeForDelay == 0) {
            delay(SHIMMER_EFFECT_DELAY) //Delay for show shimmer effect
        }

        getCategoriesUseCase().collect { list ->
            showLoading(isLoading = false)
            showCategories(categories = list.map { it.toCategoryModel() })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        uiState.value = uiState.value.copy(isLoading = isLoading)
    }

    private fun showCategories(categories: List<CategoryModel>) {
        uiState.value = uiState.value.copy(
            categories = categories,
            titleConfig = getTitleConfig(categories)
        )
    }

    private fun showSuccessMessage() {
        uiState.value = uiState.value.copy(showSuccess = true)
    }

    private fun showErrorMessage() {
        uiState.value = uiState.value.copy(showError = true)
    }

    private fun updateCategory(categoryId: Int, isSelected: Boolean) {
        val currentCategories = uiState.value.categories
        val updatedCategories = currentCategories.map { category ->
            if (category.categoryId == categoryId) {
                category.copy(isSelected = isSelected)
            } else {
                category
            }
        }
        uiState.value = uiState.value.copy(
            categories = updatedCategories,
            titleConfig = getTitleConfig(updatedCategories)
        )
    }

    private fun getTitleConfig(categories: List<CategoryModel>): SelectableTitleConfig {
        val isEnabled = categories.any { it.isSelected.orFalse }
        val titleBuilder = StringBuilder().apply {
            append(stringResourceManager.getString(R.string.text_select_category_for_pecs))
            if (isEnabled) {
                val itemsSelected = categories.filter { it.isSelected.orFalse }.size
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

    private fun updateCategoriesForPecs(categoriesModel: List<CategoryModel>) =
        viewModelScope.launch {
            if (categoriesModel.isNotEmpty()) {
                val categories = categoriesModel.map { it.toCategory() }
                updateCategoriesUseCase(categories = categories)
                    .catch { throwable ->
                        Timber.e(throwable)
                        showErrorMessage()
                    }.collect {
                        showSuccessMessage()
                    }
            }
        }
}
