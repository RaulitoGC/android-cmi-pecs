package com.cmi.data.local.mapper

import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity
import com.cmi.domain.entity.*


fun CategoryEntity.toCategory(): Category {
    return Category(
        categoryId = categoryId,
        folder = folder,
        path = path,
        name = name,
        priority = priority,
        isSelected = isSelected?.let { it > 0 } ?: false,
        isExternal = external?.let { it > 0 } ?: false
    )
}

fun PictogramEntity.toPictogram(): Pictogram {
    return Pictogram(
        pictogramId = pictogramId,
        folder = folder,
        path = path,
        name = name,
        priority = priority,
        isExternal = external?.let { it > 0 } ?: false,
        isSelected = isSelected?.let { it > 0 } ?: false,
        categoryId = categoryId
    )
}