package com.cmi.presentation.model.mapper

import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.CategorySelectableModel
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.PictogramSelectableModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun CategoryModel.toCategory(): Category = withContext(Dispatchers.IO) {
    return@withContext Category(
        categoryId = id,
        folder = folder,
        path = path,
        name = name,
        priority = priority,
        isExternal = isExternal,
        isSelected = isSelectedForPecs
    )
}

suspend fun PictogramModel.toPictogram(): Pictogram = withContext(Dispatchers.IO){
    return@withContext Pictogram(
        pictogramId = id,
        folder = folder,
        path = path,
        name = name,
        priority = priority,
        isExternal = isExternal,
        categoryId = categoryId,
        isSelected = isSelectedForPecs
    )
}

suspend fun PictogramSelectableModel.toPictogramModel(): PictogramModel = withContext(Dispatchers.IO){
    return@withContext PictogramModel(
        id = pictogramModel.id,
        folder = pictogramModel.folder,
        path = pictogramModel.path,
        name = pictogramModel.name,
        priority = pictogramModel.priority,
        isExternal = pictogramModel.isExternal,
        categoryId = pictogramModel.categoryId,
        isSelectedForPecs = isSelected
    )
}

suspend fun CategorySelectableModel.toCategoryModel(): CategoryModel = withContext(Dispatchers.IO){
    return@withContext CategoryModel(
        folder = categoryModel.folder,
        path = categoryModel.path,
        name = categoryModel.name,
        priority = categoryModel.priority,
        isExternal = categoryModel.isExternal,
        id = categoryModel.id,
        isSelectedForPecs = isSelected
    )
}