package com.cmi.presentation.model

import com.cmi.presentation.Constants.ACTION_CATEGORY_ID
import com.cmi.presentation.Constants.ATTRIBUTE_CATEGORY_ID

fun List<PictureModel>.getOrEmpty(index: Int): PictureModel {
    return if (index < size) get(index) else emptyPictureModel
}

fun List<CategoryModel>.getNotActionOrAttributeCategoryOrEmpty(index: Int): PictureModel {
    val categoryModel = if (index < size) get(index) else emptyPictureModel
    return categoryModel.takeIf { it.id != ATTRIBUTE_CATEGORY_ID && it.id != ACTION_CATEGORY_ID }
        ?: emptyPictureModel
}

fun List<CategoryModel>.getActionCategoryOrEmpty(isLoading: Boolean): PictureModel {
    if (isLoading) {
        return emptyPictureModel
    }
    val categoryModel = firstOrNull { it.id == ACTION_CATEGORY_ID }
    return categoryModel ?: emptyPictureModel
}

fun List<CategoryModel>.getAttributeCategoryOrEmpty(isLoading: Boolean): PictureModel {
    if (isLoading) {
        return emptyPictureModel
    }
    val categoryModel = firstOrNull { it.id == ATTRIBUTE_CATEGORY_ID }
    return categoryModel ?: emptyPictureModel
}

fun getEmptyPictureModel(): PictureModel {
    return emptyPictureModel
}

private val emptyPictureModel = CategoryModel(
    id = -1,
    name = "",
    isSelectedForPecs = false,
    isSelectedForUiEnabled = false,
    folder = null,
    path = null,
    priority = 0,
    isExternal = false
)