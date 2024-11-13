package com.cmi.presentation.components.chooser.category

import com.cmi.presentation.Constants.ACTION_CATEGORY_ID
import com.cmi.presentation.Constants.ATTRIBUTE_CATEGORY_ID
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
    fun getCategoriesSize() = categories.size.takeIf { it > 0 } ?: DEFAULT_PICTURE_SIZE_SHIMMER

    fun getNonActionNorAttributeCategoriesSize(): Int {
        val size  = getNonActionNorAttributeCategories().size
        val result = size.takeIf { it > 0 } ?: DEFAULT_PICTURE_SIZE_SHIMMER
        return  result
    }

    fun getNonActionNorAttributeCategories(): List<CategoryModel> {
        return categories.filterNot {
            it.id == ACTION_CATEGORY_ID || it.id == ATTRIBUTE_CATEGORY_ID
        }.filter {
            it.isSelectedForPecs.orFalse
        }
    }

    fun showAsHorizontalGrid() = categoryChooserHost is CategoryChooserHost.PecsFlow
}