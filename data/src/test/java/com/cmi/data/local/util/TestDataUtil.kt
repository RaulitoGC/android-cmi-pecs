package com.cmi.data.local.util

import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity
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

    private val categoryEntity = CategoryEntity(
        categoryId = CATEGORY_ID,
        folder = FOLDER,
        path = PATH,
        name = NAME,
        priority = PRIORITY,
        isSelected = if(IS_SELECTED) 1 else 0,
        external = if(IS_EXTERNAL) 1 else 0
    )

    private val category = Category(
        categoryId = CATEGORY_ID,
        folder = FOLDER,
        path = PATH,
        name = NAME,
        priority = PRIORITY,
        isExternal = IS_EXTERNAL,
        isSelected = IS_SELECTED
    )

    private val pictogramEntity = PictogramEntity(
        pictogramId = PICTOGRAM_ID,
        folder = FOLDER,
        path = PATH,
        name = NAME,
        priority = PRIORITY,
        isSelected = if(IS_SELECTED) 1 else 0,
        categoryId = CATEGORY_ID,
        external = if(IS_EXTERNAL) 1 else 0
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

    fun getCategory(categoryId: Int? = null) : Category{
        return categoryId?.let {
            category.copy(categoryId = categoryId)
        } ?: run {
            category
        }
    }
    fun getCategoryEntity(categoryId: Int? = null) : CategoryEntity {
        return categoryId?.let {
            categoryEntity.copy(categoryId = categoryId)
        } ?: run {
            categoryEntity
        }
    }

    fun getPictogram() = pictogram
    fun getPictogramEntity() = pictogramEntity

    fun getPictograms(size: Int) : List<Pictogram> {
        val list = mutableListOf<Pictogram>()
        repeat(size) {
            list.add(getPictogram())
        }
        return list
    }

    fun getPictogramEntities(size: Int) : List<PictogramEntity> {
        val list = mutableListOf<PictogramEntity>()
        repeat(size) {
            list.add(getPictogramEntity())
        }
        return list
    }

    fun getCategories(size: Int) : List<Category> {
        val list = mutableListOf<Category>()
        for(categoryId in 1..size) {
            list.add(getCategory().copy(categoryId = categoryId))
        }
        return list
    }

    fun getCategoriesEntities(size: Int) : List<CategoryEntity> {
        val list = mutableListOf<CategoryEntity>()
        for(categoryId in 1..size) {
            list.add(getCategoryEntity().copy(categoryId = categoryId))
        }
        return list
    }
}