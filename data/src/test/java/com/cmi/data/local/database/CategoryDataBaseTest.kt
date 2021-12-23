package com.cmi.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmi.data.local.database.dao.CategoryDao
import com.cmi.data.local.util.TestDataUtil
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CategoryDataBaseTest {
    private lateinit var categoryDao: CategoryDao
    private lateinit var cmiDataBase: CmiDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        cmiDataBase = Room.inMemoryDatabaseBuilder(
            context, CmiDataBase::class.java
        ).build()
        categoryDao = cmiDataBase.categoryDao
    }

    @Test
    @Throws(Exception::class)
    fun `insert and get category from database`() = runBlocking {
        val categoryId = 1
        val categoryEntityToInsert = TestDataUtil.getCategoryEntity(categoryId = categoryId)

        categoryDao.insertCategory(categoryEntity = categoryEntityToInsert)

        val categoryEntity = categoryDao.getCategoriesEntities().firstOrNull { it.categoryId == categoryId }
        assert(categoryEntityToInsert == categoryEntity)
    }

    @Test
    @Throws(Exception::class)
    fun `get categories from database`() = runBlocking {
        val size = 10
        val categoriesToInsert = TestDataUtil.getCategoriesEntities(size)

        for (idx in 0 until size) {
            categoryDao.insertCategory(categoryEntity = categoriesToInsert[idx])
        }

        val categoriesEntities = categoryDao.getCategoriesEntities()
        assert(categoriesToInsert == categoriesEntities)
    }

    @Test
    @Throws(Exception::class)
    fun `update category priority property in database`() = runBlocking {
        val categoryId = 1
        val categoryEntityToInsert = TestDataUtil.getCategoryEntity(categoryId = categoryId)

        val newPriority = 100
        categoryDao.insertCategory(categoryEntity = categoryEntityToInsert)
        categoryDao.updateCategoryPriority(categoryId = categoryId, 100)

        val categoryEntity = categoryDao.getCategoriesEntities().firstOrNull { it.categoryId == categoryId }
        assert(categoryEntity != null && categoryEntity.priority == newPriority) {
            print("categoryEntity from database is null or ${categoryEntity?.priority} != $newPriority")
        }
    }

    @Test
    @Throws(Exception::class)
    fun `update category in database`() = runBlocking {
        val categoryId = 1
        val categoryEntityToInsert = TestDataUtil.getCategoryEntity(categoryId = categoryId)

        val newFolderName = "new_folder_name"
        val newPriority = 200
        val newName = "new_name"
        categoryDao.insertCategory(categoryEntity = categoryEntityToInsert)
        categoryDao.updateCategory(
            categoryEntity = categoryEntityToInsert.copy(
                folder = newFolderName,
                priority = newPriority,
                name = newName
            )
        )

        val categoryEntity = categoryDao.getCategoriesEntities().firstOrNull { it.categoryId == categoryId }
        assert(categoryEntity != null) {
            print("categoryEntity from database is null")
        }

        assert(categoryEntity?.folder == newFolderName) {
            print("categoryEntity's folder != $newFolderName")
        }

        assert(categoryEntity?.priority == newPriority) {
            print("categoryEntity's priority != $newPriority")
        }

        assert(categoryEntity?.name == newName) {
            print("categoryEntity's name != $newName")
        }
    }

    @Test
    @Throws(Exception::class)
    fun `update categories in database`() = runBlocking {
        val size = 10
        val categoriesToInsert = TestDataUtil.getCategoriesEntities(size)
        for (idx in 0 until size) {
            categoryDao.insertCategory(categoryEntity = categoriesToInsert[idx])
        }

        val newFolderName = "new_folder_name"
        val newPriority = 200
        val newName = "new_name"
        val categoriesToUpdate = categoriesToInsert.map {
            it.copy(folder = newFolderName, priority = newPriority, name = newName)
        }
        categoryDao.updateCategories(categoriesToUpdate)

        val categoriesEntities = categoryDao.getCategoriesEntities()
        assert(categoriesEntities == categoriesToUpdate)
    }

    @Test
    @Throws(Exception::class)
    fun `delete category from database`() = runBlocking {
        val categoryId = 1
        val categoryEntityToInsert = TestDataUtil.getCategoryEntity(categoryId = categoryId)

        categoryDao.insertCategory(categoryEntity = categoryEntityToInsert)
        val categoryFromDataBaseInserted =
            categoryDao.getCategoriesEntities().firstOrNull { it.categoryId == categoryId }
        assert(categoryFromDataBaseInserted != null)

        categoryDao.delete(categoryEntityToInsert)
        val categoryFromDataBaseDeleted =
            categoryDao.getCategoriesEntities().firstOrNull { it.categoryId == categoryId }
        assert(categoryFromDataBaseDeleted == null)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        cmiDataBase.close()
    }
}