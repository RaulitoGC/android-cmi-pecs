package com.cmi.presentation.config.category.select

import com.cmi.presentation.model.CategoryModel

sealed class SelectCategoriesForPecsEvent {
    data class CategorySelection(
        val isSelected: Boolean,
        val categoryId: Int
    ): SelectCategoriesForPecsEvent()

    data object UpdateCategories: SelectCategoriesForPecsEvent()

    data object SuccessMessageShown: SelectCategoriesForPecsEvent()
    data object ErrorMessageShown: SelectCategoriesForPecsEvent()
}
