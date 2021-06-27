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
        categoryId = categoryId,
        folder = folder,
        path = path,
        name = name,
        priority = priority,
        isExternal = isExternal,
        isSelected = isSelected
    )
}

suspend fun PictogramModel.toPictogram(): Pictogram = withContext(Dispatchers.IO){
    return@withContext Pictogram(
        pictogramId = pictogramId,
        folder = folder,
        path = path,
        name = name,
        priority = priority,
        isExternal = isExternal,
        categoryId = categoryId,
        isSelected = isSelected
    )
}

suspend fun PictogramSelectableModel.toPictogramModel(): PictogramModel = withContext(Dispatchers.IO){
    return@withContext PictogramModel(
        pictogramId = pictogramModel.pictogramId,
        folder = pictogramModel.folder,
        path = pictogramModel.path,
        name = pictogramModel.name,
        priority = pictogramModel.priority,
        isExternal = pictogramModel.isExternal,
        categoryId = pictogramModel.categoryId,
        isSelected = isSelected
    )
}

suspend fun CategorySelectableModel.toCategoryModel(): CategoryModel = withContext(Dispatchers.IO){
    return@withContext CategoryModel(
        folder = categoryModel.folder,
        path = categoryModel.path,
        name = categoryModel.name,
        priority = categoryModel.priority,
        isExternal = categoryModel.isExternal,
        categoryId = categoryModel.categoryId,
        isSelected = isSelected
    )
}