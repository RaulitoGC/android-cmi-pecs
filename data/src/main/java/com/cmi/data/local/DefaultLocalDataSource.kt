package com.cmi.data.local

import com.cmi.data.local.database.CmiDataBase
import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity
import com.cmi.data.local.preferences.CmiPreferences
import kotlinx.coroutines.flow.flow

class DefaultLocalDataSource(
    private val cmiDataBase: CmiDataBase,
    private val cmiPreferences: CmiPreferences
) : LocalDataSource {

    override suspend fun getCategories() = flow {
        emit(cmiDataBase.categoryDao.getCategories())
    }

    override suspend fun getPictogramsByCategory(categoryId: Int) = flow {
        val pictograms = cmiDataBase.pictogramDao.getPictogramsByCategory(categoryId = categoryId)
        emit(pictograms)
    }

    override suspend fun getPictograms() = flow {
        emit(cmiDataBase.pictogramDao.getPictograms())
    }

    override suspend fun getPictogram(pictogramId: Int): PictogramEntity {
        return cmiDataBase.pictogramDao.getPictogram(pictogramId = pictogramId)
    }

    override suspend fun addPictogramEntity(pictogramEntity: PictogramEntity) = flow {
        emit(cmiDataBase.pictogramDao.insertPictogram(pictogramEntity = pictogramEntity))
    }

    override suspend fun addCategoryEntity(categoryEntity: CategoryEntity) = flow {
        emit(cmiDataBase.categoryDao.insertCategory(categoryEntity = categoryEntity))
    }

    override suspend fun updatePictogramEntity(pictogramEntity: PictogramEntity) = flow {
        emit(cmiDataBase.pictogramDao.updatePictogram(pictogramEntity = pictogramEntity))
    }

    override suspend fun updateCategoryEntity(categoryEntity: CategoryEntity) = flow {
        emit(cmiDataBase.categoryDao.updateCategory(categoryEntity = categoryEntity))
    }

    // TODO (rguzmanc ) : Remove / Move increment priority logic to domain layer
    override suspend fun updatePictogramPriority(pictogramEntity: PictogramEntity) = flow {
        val pictogramId = pictogramEntity.pictogramId ?: 0
        val newPriority = (pictogramEntity.priority ?: 0) + 1

        emit(
            cmiDataBase.pictogramDao.updatePictogramPriority(
                pictogramId = pictogramId,
                newPriority = newPriority
            )
        )
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

    override suspend fun updateCategories(categoriesEntities: List<CategoryEntity>) {
        cmiDataBase.categoryDao.updateCategories(categoriesEntities = categoriesEntities)
    }

    override suspend fun updatePictograms(pictogramsEntities: List<PictogramEntity>) {
        cmiDataBase.pictogramDao.updatePictograms(pictogramsEntities = pictogramsEntities)
    }

    override suspend fun deletePictograms(pictogramEntities: List<PictogramEntity>) {
        pictogramEntities.forEach { pictogramEntity ->
            cmiDataBase.pictogramDao.delete(pictogramEntity = pictogramEntity)
        }
    }

    override suspend fun deleteCategories(categoriesEntities: List<CategoryEntity>) {
        categoriesEntities.forEach { categoryEntity ->
            cmiDataBase.categoryDao.delete(categoryEntity = categoryEntity)
            if (categoryEntity.categoryId != null) {
                val pictograms =
                    cmiDataBase.pictogramDao.getPictogramsByCategory(categoryId = categoryEntity.categoryId)
                pictograms.forEach { pictogramEntity ->
                    cmiDataBase.pictogramDao.delete(pictogramEntity = pictogramEntity)
                }
            }
        }
    }
}