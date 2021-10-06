package com.cmi.data.local.mapper

import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity
import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram

open class BaseMapperTest {
    protected fun compareCategoryEntityToCategory(
        category: Category,
        categoryEntity: CategoryEntity
    ): Boolean {
        return category.categoryId == categoryEntity.categoryId &&
                category.name == categoryEntity.name &&
                category.path == categoryEntity.path &&
                category.folder == categoryEntity.folder &&
                category.priority == categoryEntity.priority &&
                category.isExternal == (categoryEntity.external == 1) &&
                category.isSelected == (categoryEntity.isSelected == 1)
    }

    protected fun comparePictogramEntityToPictogram(
        pictogram: Pictogram,
        pictogramEntity: PictogramEntity
    ): Boolean {
        return pictogram.categoryId == pictogramEntity.categoryId &&
                pictogram.name == pictogramEntity.name &&
                pictogram.path == pictogramEntity.path &&
                pictogram.folder == pictogramEntity.folder &&
                pictogram.priority == pictogramEntity.priority &&
                pictogram.isExternal == (pictogramEntity.external == 1) &&
                pictogram.isSelected == (pictogramEntity.isSelected == 1)
    }
}