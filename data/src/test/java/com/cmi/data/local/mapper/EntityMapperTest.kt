package com.cmi.data.local.mapper

import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity
import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EntityMapperTest : BaseMapperTest() {

    @Test
    fun `map from category entity to category`(){
        val categoryEntity = MapperData.categoryEntity
        val category = categoryEntity.toCategory()
        assert(compareCategoryEntityToCategory(category, categoryEntity)){
            "$category and $categoryEntity are not equals"
        }
    }

    @Test
    fun `map from pictogram entity to pictogram`(){
        val pictogramEntity = MapperData.pictogramEntity
        val pictogram = pictogramEntity.toPictogram()
        assert(comparePictogramEntityToPictogram(pictogram, pictogramEntity)){
            "$pictogram and $pictogramEntity are not equals"
        }
    }
}