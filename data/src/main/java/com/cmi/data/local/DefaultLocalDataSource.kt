package com.cmi.data.local

import com.cmi.data.local.database.CmiDataBase
import com.cmi.data.local.mapper.toCategory
import com.cmi.data.local.mapper.toCategoryEntity
import com.cmi.data.local.mapper.toPictogram
import com.cmi.data.local.mapper.toPictogramEntity
import com.cmi.data.local.preferences.CmiPreferences
import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram
import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.flow.flow

class DefaultLocalDataSource(
    private val cmiDataBase: CmiDataBase,
    private val cmiPreferences: CmiPreferences
) : LocalDataSource {

    override suspend fun getCategories() = flow {
        val categories = cmiDataBase
            .categoryDao
            .getCategoriesEntities()
            .map {
                it.toCategory()
            }

        emit(categories)
    }

    override suspend fun getPictogramsByCategory(categoryId: Int) = flow {
        val pictograms = cmiDataBase.pictogramDao
            .getPictogramsEntitiesByCategory(categoryId = categoryId)
            .map {
                it.toPictogram()
            }
        emit(pictograms)
    }

    override suspend fun getPictograms() = flow {
        val pictograms = cmiDataBase
            .pictogramDao
            .getPictogramsEntities()
            .map {
                it.toPictogram()
            }

        emit(pictograms)
    }

    override suspend fun getPictogram(pictogramId: Int): Pictogram {
        return cmiDataBase.pictogramDao.getPictogramEntity(pictogramId = pictogramId).toPictogram()
    }

    override suspend fun addPictogram(pictogram: Pictogram) = flow {
        emit(cmiDataBase.pictogramDao.insertPictogram(pictogramEntity = pictogram.toPictogramEntity()))
    }

    override suspend fun addCategory(category: Category) = flow {
        emit(cmiDataBase.categoryDao.insertCategory(categoryEntity = category.toCategoryEntity()))
    }

    override suspend fun updatePictogram(pictogram: Pictogram) = flow {
        emit(cmiDataBase.pictogramDao.updatePictogram(pictogramEntity = pictogram.toPictogramEntity()))
    }

    override suspend fun updateCategory(category: Category) = flow {
        emit(cmiDataBase.categoryDao.updateCategory(categoryEntity = category.toCategoryEntity()))
    }

    override fun getMainPictogramId(): Int = cmiPreferences.getPictogramMainId()
    override fun setMainPictogramId(pictogramId: Int) = flow {
        emit(cmiPreferences.setPictogramMainId(pictogramId = pictogramId))
    }

    override fun getFirstActionPictogramId(): Int = cmiPreferences.getPictogramFirstActionId()
    override fun setFirstActionPictogramId(pictogramId: Int) = flow {
        emit(cmiPreferences.setPictogramFirstActionId(pictogramId = pictogramId))
    }

    override fun getSecondActionPictogramId(): Int = cmiPreferences.getPictogramSecondActionId()
    override fun setSecondActionPictogramId(pictogramId: Int) = flow {
        emit(cmiPreferences.setPictogramSecondActionId(pictogramId = pictogramId))
    }

    override fun getAttributePictogramId(): Int = cmiPreferences.getPictogramAttributeId()
    override fun setAttributePictogramId(pictogramId: Int) = flow {
        emit(cmiPreferences.setPictogramAttributeId(pictogramId = pictogramId))
    }

    override fun getSecondPictogramAttributeId(): Int =
        cmiPreferences.getSecondPictogramAttributeId()

    override fun setSecondAttributePictogramId(pictogramId: Int) = flow {
        emit(cmiPreferences.setSecondPictogramAttributeId(pictogramId = pictogramId))
    }

    override suspend fun updateCategories(categories: List<Category>) = flow {
        val categoriesEntities = categories.map {
            it.toCategoryEntity()
        }
        emit(cmiDataBase.categoryDao.updateCategories(categoriesEntities = categoriesEntities))
    }

    override suspend fun updatePictograms(pictograms: List<Pictogram>) = flow {
        val pictogramsEntities = pictograms.map {
            it.toPictogramEntity()
        }
        emit(cmiDataBase.pictogramDao.updatePictograms(pictogramsEntities = pictogramsEntities))
    }

    override suspend fun deletePictograms(pictograms: List<Pictogram>) = flow {
        pictograms.forEach { pictogram ->
            cmiDataBase.pictogramDao.delete(pictogramEntity = pictogram.toPictogramEntity())
        }
        emit(Unit)
    }

    override suspend fun deleteCategories(categories: List<Category>) = flow {
        categories.forEach { category ->
            cmiDataBase.categoryDao.delete(categoryEntity = category.toCategoryEntity())
            val categoryId = category.categoryId
            if (categoryId != null) {
                val pictogramsEntities =
                    cmiDataBase.pictogramDao.getPictogramsEntitiesByCategory(categoryId = categoryId)
                pictogramsEntities.forEach { pictogramEntity ->
                    cmiDataBase.pictogramDao.delete(pictogramEntity = pictogramEntity)
                }
            }
        }
        emit(Unit)
    }
}