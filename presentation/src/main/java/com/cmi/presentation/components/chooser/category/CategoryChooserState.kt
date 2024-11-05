package com.cmi.presentation.components.chooser.category

import com.cmi.presentation.Constants.DEFAULT_PICTURE_SIZE_SHIMMER
import com.cmi.presentation.common.navigation.CategoryChooserHost
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.ktx.orFalse
import com.cmi.presentation.model.CategoryModel

data class CategoryChooserState(
    val categoryChooserHost: CategoryChooserHost,
    val isLoading: Boolean = false,
    val categories: List<CategoryModel> = emptyList(),
    val cardViewConfig: CardViewConfig = CardViewConfig()
){
    fun getCategoriesSize() = categories.size.takeIf { it > 0} ?: DEFAULT_PICTURE_SIZE_SHIMMER

}