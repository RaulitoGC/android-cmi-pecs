package com.cmi.data.local.mapper

import com.cmi.data.local.util.TestDataUtil
import com.cmi.data.local.util.CompareEntityModelUtil.compareCategoryEntityToCategory
import com.cmi.data.local.util.CompareEntityModelUtil.comparePictogramEntityToPictogram
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReverseEntityMapperTest {

    @Test
    fun `map from category to category entity`() {
        val category = TestDataUtil.getCategory()
        val categoryEntity = category.toCategoryEntity()
        assert(compareCategoryEntityToCategory(category, categoryEntity)) {
            "$category and $categoryEntity are not equals"
        }
    }

    @Test
    fun `map from pictogram to pictogram entity`() {
        val pictogram = TestDataUtil.getPictogram()
        val pictogramEntity = pictogram.toPictogramEntity()
        assert(comparePictogramEntityToPictogram(pictogram, pictogramEntity)) {
            "$pictogram and $pictogramEntity are not equals"
        }
    }
}