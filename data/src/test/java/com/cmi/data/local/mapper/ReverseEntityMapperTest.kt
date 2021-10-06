package com.cmi.data.local.mapper

import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity
import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReverseEntityMapperTest : BaseMapperTest(){

    @Test
    fun `map from category to category entity`() {
        val category = MapperData.category
        val categoryEntity = category.toCategoryEntity()
        assert(compareCategoryEntityToCategory(category, categoryEntity)) {
            "$category and $categoryEntity are not equals"
        }
    }

    @Test
    fun `map from pictogram to pictogram entity`() {
        val pictogram = MapperData.pictogram
        val pictogramEntity = pictogram.toPictogramEntity()
        assert(comparePictogramEntityToPictogram(pictogram, pictogramEntity)) {
            "$pictogram and $pictogramEntity are not equals"
        }
    }
}