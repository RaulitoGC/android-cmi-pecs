package com.cmi.data.local.mapper

import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity
import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram

object MapperData {

    private const val CATEGORY_ID = 1
    private const val FOLDER = "folder"
    private const val PATH = "path"
    private const val NAME = "NAME"
    private const val PRIORITY = 1
    private const val IS_SELECTED = true
    private const val IS_EXTERNAL = true

    private const val PICTOGRAM_ID = 2

    val categoryEntity = CategoryEntity(
        categoryId = CATEGORY_ID,
        folder = FOLDER,
        path = PATH,
        name = NAME,
        priority = PRIORITY,
        isSelected = if(IS_SELECTED) 1 else 0,
        external = if(IS_EXTERNAL) 1 else 0
    )

    val category = Category(
        categoryId = CATEGORY_ID,
        folder = FOLDER,
        path = PATH,
        name = NAME,
        priority = PRIORITY,
        isExternal = IS_EXTERNAL,
        isSelected = IS_SELECTED
    )

    val pictogramEntity = PictogramEntity(
        pictogramId = PICTOGRAM_ID,
        folder = FOLDER,
        path = PATH,
        name = NAME,
        priority = PRIORITY,
        isSelected = if(IS_SELECTED) 1 else 0,
        categoryId = CATEGORY_ID,
        external = if(IS_EXTERNAL) 1 else 0
    )

    val pictogram = Pictogram(
        pictogramId = PICTOGRAM_ID,
        folder = FOLDER,
        path = PATH,
        name = NAME,
        priority = PRIORITY,
        isSelected = IS_SELECTED,
        categoryId = CATEGORY_ID,
        isExternal = IS_EXTERNAL
    )
}