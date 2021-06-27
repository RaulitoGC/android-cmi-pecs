package com.cmi.data.local.mapper

import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity
import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram


fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        categoryId = categoryId,
        folder = folder,
        path = path,
        name = name,
        priority = priority,
        isSelected = if(isSelected == true) 1 else 0,
        external = if(isExternal == true) 1 else 0
    )
}

fun Pictogram.toPictogramEntity(): PictogramEntity {
    return PictogramEntity(
        pictogramId = pictogramId,
        folder = folder,
        path = path,
        name = name,
        priority = priority,
        external = if(isExternal == true) 1 else 0,
        isSelected = if(isSelected == true) 1 else 0,
        categoryId = categoryId
    )
}