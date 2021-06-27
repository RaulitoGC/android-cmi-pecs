package com.cmi.presentation.model.mapper

import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram
import com.cmi.presentation.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

suspend fun Category.toCategoryModel(): CategoryModel = withContext(Dispatchers.IO) {
    return@withContext CategoryModel(
        categoryId = categoryId,
        folder = folder,
        path = path,
        name = name,
        priority = priority,
        isExternal = isExternal,
        isSelected = isSelected
    )
}

suspend fun Pictogram.toPictogramModel(): PictogramModel = withContext(Dispatchers.IO) {
    return@withContext PictogramModel(
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

suspend fun PictogramModel.toPictogramSelectableModel(): PictogramSelectableModel = withContext(Dispatchers.IO){
    return@withContext PictogramSelectableModel(
        pictogramModel = this@toPictogramSelectableModel,
        isSelected = this@toPictogramSelectableModel.isSelected == true
    )
}

suspend fun PictogramModel.toPictogramSelectableModelUnChecked(): PictogramSelectableModel = withContext(Dispatchers.IO){
    return@withContext PictogramSelectableModel(
        pictogramModel = this@toPictogramSelectableModelUnChecked,
        isSelected = false
    )
}

suspend fun CategoryModel.toCategorySelectableModel(): CategorySelectableModel = withContext(Dispatchers.IO){
    return@withContext CategorySelectableModel(
        categoryModel = this@toCategorySelectableModel,
        isSelected = this@toCategorySelectableModel.isSelected == true
    )
}

suspend fun CategoryModel.toCategorySelectableModelUnChecked(): CategorySelectableModel = withContext(Dispatchers.IO){
    return@withContext CategorySelectableModel(
        categoryModel = this@toCategorySelectableModelUnChecked,
        isSelected = false
    )
}

suspend fun getCategoriesSelectableMapFormat(itemsPerScreen: Int, items: List<CategorySelectableModel>): Map<Int, List<CategorySelectableModel>> =
    withContext(Dispatchers.IO) {
        val size = (items.size / itemsPerScreen) + (if (items.size % itemsPerScreen != 0) 1 else 0)
        val map = HashMap<Int, List<CategorySelectableModel>>()
        for (position in 0 until size) {
            val init = position * itemsPerScreen
            val end = init + itemsPerScreen
            val list = mutableListOf<CategorySelectableModel>()
            for (pos in init until end) {
                if (pos < items.size) {
                    list.add(items[pos])
                }
            }
            map[position] = list
        }
        return@withContext map
    }

suspend fun getCategoriesMapFormat(itemsPerScreen: Int, items: List<CategoryModel>): Map<Int, List<CategoryModel>> =
    withContext(Dispatchers.IO) {
        val size = (items.size / itemsPerScreen) + (if (items.size % itemsPerScreen != 0) 1 else 0)
        val map = HashMap<Int, List<CategoryModel>>()
        for (position in 0 until size) {
            val init = position * itemsPerScreen
            val end = init + itemsPerScreen
            val list = mutableListOf<CategoryModel>()
            for (pos in init until end) {
                if (pos < items.size) {
                    list.add(items[pos])
                }
            }
            map[position] = list
        }
        return@withContext map
    }

suspend fun getPictogramsMapFormat(
    screenItems: Int,
    items: List<PictogramModel>
): Map<Int, List<PictogramModel>> =
    withContext(Dispatchers.IO) {
        val size = (items.size / screenItems) + (if (items.size % screenItems != 0) 1 else 0)
        val map = HashMap<Int, List<PictogramModel>>()
        for (position in 0 until size) {
            val init = position * screenItems
            val end = init + screenItems
            val list = mutableListOf<PictogramModel>()
            for (pos in init until end) {
                if (pos < items.size) {
                    list.add(items[pos])
                }
            }
            map[position] = list
        }
        return@withContext map
    }
