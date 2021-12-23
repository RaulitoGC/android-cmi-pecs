package com.cmi.data.local

import com.cmi.data.local.util.FakeLocalDataSource
import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test

class LocalDataSourceTest {

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
    }

    @Test
    fun `get categories from local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val categories = FakeLocalDataSource.categoriesFromDisk

        val categoriesFromDisk = localDataSource.getCategories().single()

        assert(categories == categoriesFromDisk)
    }

    @Test
    fun `get pictograms by category from local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val categoryId = 10
        val pictograms = FakeLocalDataSource.pictogramsFromDisk.map {
            it.copy(categoryId = categoryId)
        }

        val pictogramsFromDisk = localDataSource.getPictogramsByCategory(categoryId).single()

        assert(pictograms == pictogramsFromDisk)
    }

    @Test
    fun `get pictograms from local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val pictograms = FakeLocalDataSource.pictogramsFromDisk

        val pictogramsFromDisk = localDataSource.getPictograms().single()

        assert(pictograms == pictogramsFromDisk)
    }

    @Test
    fun `get pictogram from local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val pictogramId = 21
        val pictogram = FakeLocalDataSource.pictogramFromDisk.copy(pictogramId = pictogramId)

        val pictogramFromDisk = localDataSource.getPictogram(pictogramId = pictogramId)

        assert(pictogram == pictogramFromDisk)
    }

    @Test
    fun `add pictogram to local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val pictogramId = 1001
        val pictogramToAdd = FakeLocalDataSource.pictogramFromDisk.copy(pictogramId = pictogramId)

        localDataSource.addPictogram(pictogramToAdd).single()
        val pictogramFromDisk =
            localDataSource.getPictograms().single().firstOrNull { it.pictogramId == pictogramId }
        assert(pictogramFromDisk != null)

        assert(pictogramFromDisk == pictogramFromDisk)
    }

    @Test
    fun `add category to local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val categoryId = 2001
        val categoryToAdd = FakeLocalDataSource.categoryFromDisk.copy(categoryId = categoryId)

        localDataSource.addCategory(categoryToAdd).single()
        val categoryFromDisk =
            localDataSource.getCategories().single().firstOrNull { it.categoryId == categoryId }
        assert(categoryFromDisk != null)

        assert(categoryToAdd == categoryFromDisk)
    }

    @Test
    fun `update pictogram to local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val pictogramId = 1020
        val pictogramToAdd = FakeLocalDataSource.pictogramFromDisk.copy(pictogramId = pictogramId)

        localDataSource.addPictogram(pictogramToAdd).single()
        val newName = "pictogram_new_name"
        val pictogramToUpdate = pictogramToAdd.copy(name = newName)
        localDataSource.updatePictogram(pictogramToUpdate).single()
        val pictogramFromDisk =
            localDataSource.getPictograms().single().firstOrNull { it.pictogramId == pictogramId }
        assert(pictogramFromDisk != null)

        assert(pictogramToUpdate == pictogramFromDisk)
    }

    @Test
    fun `update category to local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val categoryId = 1021
        val categoryToAdd = FakeLocalDataSource.categoryFromDisk.copy(categoryId = categoryId)

        localDataSource.addCategory(categoryToAdd).single()
        val newName = "category_new_name"
        val categoryToUpdate = categoryToAdd.copy(name = newName)
        localDataSource.updateCategory(categoryToUpdate).single()
        val categoryFromDisk =
            localDataSource.getCategories().single().firstOrNull { it.categoryId == categoryId }
        assert(categoryFromDisk != null)

        assert(categoryToUpdate == categoryFromDisk)
    }

    @Test
    fun `set and get main pictogram id from local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val mainPictogramId = 1093

        localDataSource.setMainPictogramId(pictogramId = mainPictogramId).single()
        val mainPictogramIdFromDisk = localDataSource.getMainPictogramId()

        assert(mainPictogramId == mainPictogramIdFromDisk)
    }

    @Test
    fun `set and get first action pictogram id from local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val firstActionPictogramId = 1094

        localDataSource.setFirstActionPictogramId(pictogramId = firstActionPictogramId).single()
        val firstActionPictogramIdFromDisk = localDataSource.getFirstActionPictogramId()

        assert(firstActionPictogramId == firstActionPictogramIdFromDisk)
    }

    @Test
    fun `set and get second action pictogram id from local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val secondActionPictogramId = 1095

        localDataSource.setSecondActionPictogramId(pictogramId = secondActionPictogramId).single()
        val secondActionPictogramIdFromDisk = localDataSource.getSecondActionPictogramId()

        assert(secondActionPictogramId == secondActionPictogramIdFromDisk)
    }

    @Test
    fun `set and get attribute pictogram id from local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val attributePictogramId = 1096

        localDataSource.setAttributePictogramId(pictogramId = attributePictogramId).single()
        val attributePictogramIdFromDisk = localDataSource.getAttributePictogramId()

        assert(attributePictogramId == attributePictogramIdFromDisk)
    }

    @Test
    fun `set and get second attribute pictogram id from local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val secondAttributePictogramId = 1097

        localDataSource.setSecondAttributePictogramId(pictogramId = secondAttributePictogramId)
            .single()
        val secondAttributePictogramIdFromDisk = localDataSource.getSecondPictogramAttributeId()

        assert(secondAttributePictogramId == secondAttributePictogramIdFromDisk)
    }

    @Test
    fun `update categories in local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val categoriesAdded = FakeLocalDataSource.categoriesFromDisk

        val newName = "category_new_name"
        val categoriesToUpdate = categoriesAdded.map {
            it.copy(name = newName)
        }
        localDataSource.updateCategories(categoriesToUpdate).single()
        val categoriesFromDisk = localDataSource.getCategories().single()

        assert(categoriesToUpdate == categoriesFromDisk)
    }

    @Test
    fun `update pictograms in local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val pictogramsAdded = FakeLocalDataSource.pictogramsFromDisk

        val newName = "new_pictogram_name"
        val pictogramsToUpdate = pictogramsAdded.map {
            it.copy(name = newName)
        }
        println("FIRST SIZE = ${pictogramsToUpdate.size}")
        println("SECOND SIZE = ${localDataSource.getPictograms().single().size}")
        localDataSource.updatePictograms(pictogramsToUpdate).single()
        val pictogramsFromDisk = localDataSource.getPictograms().single().intersect(
            pictogramsToUpdate.toSet()
        )
        print(pictogramsToUpdate)
        print(pictogramsFromDisk)
        assertArrayEquals(pictogramsToUpdate.toTypedArray(), pictogramsFromDisk.toTypedArray())
    }

    @Test
    fun `delete pictograms from local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val pictogramsAdded = FakeLocalDataSource.pictogramsFromDisk

        localDataSource.deletePictograms(pictogramsAdded).single()
        val pictogramsFromDisk = localDataSource.getPictograms().single()

        val intersection = pictogramsFromDisk.intersect(pictogramsAdded.toSet())

        assert(intersection.isEmpty())
    }

    @Test
    fun `delete categories from local data source`() = runBlocking {
        FakeLocalDataSource.reset()
        val categoriesAdded = FakeLocalDataSource.categoriesFromDisk

        localDataSource.deleteCategories(categoriesAdded).single()
        val categoriesFromDisk = localDataSource.getCategories().single()

        val intersection = categoriesFromDisk.intersect(categoriesAdded.toSet())

        assert(intersection.isEmpty())
    }
}