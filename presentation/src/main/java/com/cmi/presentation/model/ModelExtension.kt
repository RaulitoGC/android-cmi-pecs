package com.cmi.presentation.model

fun List<PictureModel>.getOrEmpty(index: Int): PictureModel {
    return if (index < size) get(index) else emptyPictureModel
}

fun getEmptyPictureModel(): PictureModel {
    return emptyPictureModel
}

private val emptyPictureModel = CategoryModel(
    id = 0,
    name = "",
    isSelectedForPecs = false,
    isSelectedForUiEnabled = false,
    folder = null,
    path = null,
    priority = 0,
    isExternal = false
)