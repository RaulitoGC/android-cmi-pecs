package com.cmi.presentation.components.category

import com.cmi.presentation.ktx.orFalse
import com.cmi.presentation.model.CategoryModel

data class CategorySelectableState(
    val isLoading: Boolean = false,
    val categories: List<CategoryModel> = emptyList()
){
    fun getCategoriesSize() = categories.size.takeIf { it > 0} ?: DEFAULT_CATEGORY_SIZE_SHIMMER

    fun getCategoriesSelected() = categories.filter { it.isSelected.orFalse }

    companion object {
        private const val DEFAULT_CATEGORY_SIZE_SHIMMER = 20
    }
}

