package com.cmi.presentation.components.category

import com.cmi.presentation.model.CategoryModel

data class CategorySelectableState(
    val isLoading: Boolean = false,
    val categories: List<CategoryModel> = emptyList()
)

