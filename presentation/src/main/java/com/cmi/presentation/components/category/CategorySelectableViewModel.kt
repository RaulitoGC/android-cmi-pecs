package com.cmi.presentation.components.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.presentation.Constants
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.mapper.toCategoryModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CategorySelectableViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    val uiState = MutableStateFlow(CategorySelectableState())

    init {
        getCategories()
    }

    fun handleEvent(event: CategorySelectableEvent) {
        when (event) {
            is CategorySelectableEvent.ShowLoading -> showLoading(
                isLoading = event.isLoading
            )
        }
    }

    private fun getCategories() = viewModelScope.launch{
        showLoading(isLoading = true)
        val timeForDelay = uiState.value.categories.size
        if(timeForDelay == 0){
            delay(Constants.SHIMMER_EFFECT_DELAY) //Delay for show shimmer effect
            showLoading(isLoading = false)
        }

        getCategoriesUseCase().collect { list ->
            showCategories(categories = list.map { it.toCategoryModel() })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        uiState.value = uiState.value.copy(isLoading = isLoading)
    }

    private fun showCategories(categories: List<CategoryModel>) {
        uiState.value = uiState.value.copy(categories = categories)
    }
}
