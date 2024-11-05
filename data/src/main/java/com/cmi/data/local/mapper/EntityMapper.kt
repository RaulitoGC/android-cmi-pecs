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
        isSelectedForPecs = isSelectedForPecs?.let { it > 0 } ?: false,
        isExternal = isExternal?.let { it > 0 } ?: false,
        isFoundationPath = isFoundationPath?.let { it > 0 } ?: false,
    )
}

fun PictogramEntity.toPictogram(): Pictogram {
    return Pictogram(
        pictogramId = pictogramId,
        folder = folder,
        path = path,
        name = name,
        priority = priority,
        isExternal = isExternal?.let { it > 0 } ?: false,
        isSelectedForPecs = isSelectedForPecs?.let { it > 0 } ?: false,
        categoryId = categoryId,
        isFoundationPath = isFoundationPath?.let { it > 0 } ?: false,
    )
}