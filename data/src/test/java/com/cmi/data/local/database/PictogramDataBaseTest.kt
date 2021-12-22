package com.cmi.data.local.database

import android.content.Context
import androidx.room.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmi.data.local.database.dao.PictogramDao
import com.cmi.data.local.util.TestDataUtil
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PictogramDataBaseTest {
    private lateinit var pictogramDao: PictogramDao
    private lateinit var cmiDataBase: CmiDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        cmiDataBase = Room.inMemoryDatabaseBuilder(
            context, CmiDataBase::class.java
        ).build()
        pictogramDao = cmiDataBase.pictogramDao
    }

    @Test
    @Throws(Exception::class)
    fun `insert and get pictogram from database`() = runBlocking {
        val pictogramId = 1
        val pictogramEntityToInsert = TestDataUtil.getPictogramEntity(pictogramId = pictogramId)

        pictogramDao.insertPictogram(pictogramEntity = pictogramEntityToInsert)

        val pictogramEntityFromDataBase = pictogramDao.getPictogram(pictogramId = pictogramId)
        assert(pictogramEntityFromDataBase == pictogramEntityToInsert)
    }

    @Test
    @Throws(Exception::class)
    fun `get pictograms from database`() = runBlocking {
        val size = 10
        val pictogramsEntitiesToInsert = TestDataUtil.getPictogramEntities(size)

        for (idx in 0 until size) {
            pictogramDao.insertPictogram(pictogramEntity = pictogramsEntitiesToInsert[idx])
        }

        val pictogramsEntitiesFromDataBase = pictogramDao.getPictograms()
        assert(pictogramsEntitiesToInsert == pictogramsEntitiesFromDataBase)
    }

    @Test
    @Throws(Exception::class)
    fun `get pictograms by category from database`() = runBlocking {
        val size = 10
        val categoryId = 23
        val pictogramsEntitiesToInsert = TestDataUtil.getPictogramEntities(size).map {
            it.copy(categoryId = categoryId)
        }

        for (idx in 0 until size) {
            pictogramDao.insertPictogram(pictogramEntity = pictogramsEntitiesToInsert[idx])
        }

        val pictogramsEntitiesFromDataBase =
            pictogramDao.getPictogramsByCategory(categoryId = categoryId)
        assert(pictogramsEntitiesToInsert == pictogramsEntitiesFromDataBase)
    }

    @Test
    @Throws(Exception::class)
    fun `update pictogram priority property in database`() = runBlocking {
        val pictogramId = 1
        val pictogramEntityToInsert = TestDataUtil.getPictogramEntity(pictogramId = pictogramId)

        val newPriority = 100
        pictogramDao.insertPictogram(pictogramEntity = pictogramEntityToInsert)
        pictogramDao.updatePictogramPriority(pictogramId = pictogramId, newPriority = newPriority)

        val pictogramEntity = pictogramDao.getPictogram(pictogramId = pictogramId)
        assert(pictogramEntity.priority == newPriority) {
            print(" ${pictogramEntity.priority} != $newPriority")
        }
    }

    @Test
    @Throws(Exception::class)
    fun `update pictogram in database`() = runBlocking {
        val pictogramId = 1
        val pictogramEntityToInsert = TestDataUtil.getPictogramEntity(pictogramId = pictogramId)

        val newFolderName = "new_folder_name"
        val newPriority = 200
        val newName = "new_name"
        pictogramDao.insertPictogram(pictogramEntity = pictogramEntityToInsert)
        pictogramDao.updatePictogram(
            pictogramEntity = pictogramEntityToInsert.copy(
                folder = newFolderName,
                priority = newPriority,
                name = newName
            )
        )

        val pictogramEntityFromDataBase = pictogramDao.getPictogram(pictogramId = pictogramId)

        assert(pictogramEntityFromDataBase.folder == newFolderName) {
            print("pictogramEntityFromDataBase's folder != $newFolderName")
        }

        assert(pictogramEntityFromDataBase.priority == newPriority) {
            print("pictogramEntityFromDataBase's priority != $newPriority")
        }

        assert(pictogramEntityFromDataBase.name == newName) {
            print("pictogramEntityFromDataBase's name != $newName")
        }
    }

    @Test
    @Throws(Exception::class)
    fun `update pictograms in database`() = runBlocking {
        val size = 10
        val pictogramsToInsert = TestDataUtil.getPictogramEntities(size)
        for (idx in 0 until size) {
            pictogramDao.insertPictogram(pictogramEntity = pictogramsToInsert[idx])
        }

        val newFolderName = "new_folder_name"
        val newPriority = 200
        val newName = "new_name"
        val pictogramsToUpdate = pictogramsToInsert.map {
            it.copy(folder = newFolderName, priority = newPriority, name = newName)
        }
        pictogramDao.updatePictograms(pictogramsToUpdate)

        val pictogramEntitiesFromDataBase = pictogramDao.getPictograms()
        assert(pictogramsToUpdate == pictogramEntitiesFromDataBase)
    }

    @Test
    @Throws(Exception::class)
    fun `delete pictogram from database`() = runBlocking {
        val pictogramId = 1
        val pictogramEntityToInsert = TestDataUtil.getPictogramEntity(pictogramId = pictogramId)

        pictogramDao.insertPictogram(pictogramEntityToInsert)
        val pictogramFromDataBaseInserted = pictogramDao.getPictogram(pictogramId)
        assert(pictogramFromDataBaseInserted != null)
        pictogramDao.delete(pictogramFromDataBaseInserted)

        val pictogramFromDataBaseDeleted =
            pictogramDao.getPictogram(pictogramId)
        assert(pictogramFromDataBaseDeleted == null)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        cmiDataBase.close()
    }
}