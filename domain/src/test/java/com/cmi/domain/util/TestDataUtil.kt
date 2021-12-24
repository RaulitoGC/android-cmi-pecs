package com.cmi.domain.util

import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram

object TestDataUtil {

    private const val CATEGORY_ID = 1
    private const val FOLDER = "folder"
    private const val PATH = "path"
    private const val NAME = "NAME"
    private const val PRIORITY = 1
    private const val IS_SELECTED = true
    private const val IS_EXTERNAL = true

    private const val PICTOGRAM_ID = 2

    private val category = Category(
        categoryId = CATEGORY_ID,
        folder = FOLDER,
        path = PATH,
        name = NAME,
        priority = PRIORITY,
        isExternal = IS_EXTERNAL,
        isSelected = IS_SELECTED
    )

    private val pictogram = Pictogram(
        pictogramId = PICTOGRAM_ID,
        folder = FOLDER,
        path = PATH,
        name = NAME,
        priority = PRIORITY,
        isSelected = IS_SELECTED,
        categoryId = CATEGORY_ID,
        isExternal = IS_EXTERNAL
    )

    fun getCategory(categoryId: Int? = null): Category {
        return categoryId?.let {
            category.copy(categoryId = categoryId)
        } ?: run {
            category
        }
    }

    fun getPictogram(pictogramId: Int? = null): Pictogram {
        return pictogramId?.let {
            pictogram.copy(pictogramId = pictogramId)
        } ?: run {
            pictogram
        }
    }

    fun getPictograms(size: Int): List<Pictogram> {
        val list = mutableListOf<Pictogram>()
        for (pictogramId in 1..size) {
            list.add(getPictogram().copy(pictogramId = pictogramId))
        }
        return list
    }

    fun getCategories(size: Int): List<Category> {
        val list = mutableListOf<Category>()
        for (categoryId in 1..size) {
            list.add(getCategory().copy(categoryId = categoryId))
        }
        return list
    }
}