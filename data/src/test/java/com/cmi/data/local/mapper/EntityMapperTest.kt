package com.cmi.data.local.mapper

import com.cmi.data.local.util.TestDataUtil
import com.cmi.data.local.util.CompareEntityModelUtil.compareCategoryEntityToCategory
import com.cmi.data.local.util.CompareEntityModelUtil.comparePictogramEntityToPictogram
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EntityMapperTest {

    @Test
    fun `mapper from category entity to category`() {
        val categoryEntity = TestDataUtil.getCategoryEntity()
        val category = categoryEntity.toCategory()
        assert(compareCategoryEntityToCategory(category, categoryEntity)) {
            "$category and $categoryEntity are not equals"
        }
    }

    @Test
    fun `mapper from pictogram entity to pictogram`() {
        val pictogramEntity = TestDataUtil.getPictogramEntity()
        val pictogram = pictogramEntity.toPictogram()
        assert(comparePictogramEntityToPictogram(pictogram, pictogramEntity)) {
            "$pictogram and $pictogramEntity are not equals"
        }
    }
}