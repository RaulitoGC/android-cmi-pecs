package com.cmi.data.local

import com.cmi.data.local.database.CmiDataBase
import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity
import com.cmi.data.local.preferences.CmiPreferences
import kotlinx.coroutines.flow.flow

class LocalDataSource(
    private val cmiDataBase: CmiDataBase,
    private val cmiPreferences: CmiPreferences
){

    suspend fun getCategories() = flow {
        emit(cmiDataBase.categoryDao.getCategories())
    }

    suspend fun getPictogramsByCategory(categoryId: Int) = flow {
        val pictograms = cmiDataBase.pictogramDao.getPictogramsByCategory(categoryId = categoryId)
        emit(pictograms)
    }

    suspend fun getPictograms() = flow {
        emit(cmiDataBase.pictogramDao.getPictograms())
    }

    suspend fun getPictogram(pictogramId: Int) : PictogramEntity{
        return cmiDataBase.pictogramDao.getPictogram(pictogramId = pictogramId)
    }

    suspend fun addPictogramEntity(pictogramEntity: PictogramEntity) = flow {
        emit(cmiDataBase.pictogramDao.insertPictogram(pictogramEntity = pictogramEntity))
    }

    suspend fun addCategoryEntity(categoryEntity: CategoryEntity) = flow {
        emit(cmiDataBase.categoryDao.insertCategory(categoryEntity = categoryEntity))
    }

    suspend fun updatePictogramEntity(pictogramEntity: PictogramEntity) = flow {
        emit(cmiDataBase.pictogramDao.updatePictogram(pictogramEntity = pictogramEntity))
    }

    suspend fun updateCategoryEntity(categoryEntity: CategoryEntity) = flow {
        emit(cmiDataBase.categoryDao.updateCategory(categoryEntity = categoryEntity))
    }

    suspend fun updatePictogramPriority(pictogramEntity: PictogramEntity) = flow {
        val pictogramId = pictogramEntity.pictogramId ?: 0
        val newPriority = (pictogramEntity.priority ?: 0) + 1

        emit(
            cmiDataBase.pictogramDao.updatePictogramPriority(
                pictogramId = pictogramId,
                newPriority = newPriority
            )
        )
    }

    fun getMainPictogramId(): Int = cmiPreferences.getPictogramMainId()
    fun setMainPictogramId(pictogramId: Int) = flow {
        emit(cmiPreferences.setPictogramMainId(pictogramId = pictogramId))
    }

    fun getFirstActionPictogramId() : Int = cmiPreferences.getPictogramFirstActionId()
    fun setFirstActionPictogramId(pictogramId: Int) = flow {
        emit(cmiPreferences.setPictogramFirstActionId(pictogramId = pictogramId))
    }

    fun getSecondActionPictogramId() : Int = cmiPreferences.getPictogramSecondActionId()
    fun setSecondActionPictogramId(pictogramId: Int) = flow {
        emit(cmiPreferences.setPictogramSecondActionId(pictogramId = pictogramId))
    }

    fun getAttributePictogramId(): Int = cmiPreferences.getPictogramAttributeId()
    fun setAttributePictogramId(pictogramId: Int) = flow {
        emit(cmiPreferences.setPictogramAttributeId(pictogramId = pictogramId))
    }

    fun getSecondPictogramAttributeId() : Int = cmiPreferences.getSecondPictogramAttributeId()
    fun setSecondAttributePictogramId(pictogramId: Int) = flow {
        emit(cmiPreferences.setSecondPictogramAttributeId(pictogramId = pictogramId))
    }

    suspend fun updateCategories(categoriesEntities: List<CategoryEntity>) {
        cmiDataBase.categoryDao.updateCategories(categoriesEntities = categoriesEntities)
    }

    suspend fun updatePictograms(pictogramsEntities: List<PictogramEntity>) {
        cmiDataBase.pictogramDao.updatePictograms(pictogramsEntities = pictogramsEntities)
    }

    suspend fun deletePictograms(pictogramEntities: List<PictogramEntity>){
        pictogramEntities.forEach {pictogramEntity ->
            cmiDataBase.pictogramDao.delete(pictogramEntity = pictogramEntity)
        }
    }

    suspend fun deleteCategories(categoriesEntities: List<CategoryEntity>){
        categoriesEntities.forEach { categoryEntity ->
            cmiDataBase.categoryDao.delete(categoryEntity = categoryEntity)
            if(categoryEntity.categoryId != null){
                val pictograms = cmiDataBase.pictogramDao.getPictogramsByCategory(categoryId = categoryEntity.categoryId)
                pictograms.forEach { pictogramEntity ->
                    cmiDataBase.pictogramDao.delete(pictogramEntity = pictogramEntity)
                }
            }
        }
    }
}