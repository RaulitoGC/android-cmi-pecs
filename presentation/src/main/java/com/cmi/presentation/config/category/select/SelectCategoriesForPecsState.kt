package com.cmi.presentation.config.category.select

import com.cmi.presentation.config.add.model.SelectableTitleConfig
import com.cmi.presentation.model.CategoryModel

data class SelectCategoriesForPecsState(
    val isLoading: Boolean = false,
    val categories: List<CategoryModel> = emptyList(),
    val titleConfig: SelectableTitleConfig = SelectableTitleConfig(
        title = "",
        isActionEnabled = false
    ),
    val showError: Boolean? = null,
    val showSuccess: Boolean? = null
) {
    fun getCategoriesSize() = categories.size.takeIf { it > 0} ?: DEFAULT_CATEGORY_SIZE_SHIMMER

    companion object {
        private const val DEFAULT_CATEGORY_SIZE_SHIMMER = 20
    }
}
