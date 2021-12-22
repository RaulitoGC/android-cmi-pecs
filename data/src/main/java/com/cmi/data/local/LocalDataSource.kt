package com.cmi.data.local

import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getCategories(): Flow<List<CategoryEntity>>

    suspend fun getPictogramsByCategory(categoryId: Int): Flow<List<PictogramEntity>>

    suspend fun getPictograms(): Flow<List<PictogramEntity>>

    suspend fun getPictogram(pictogramId: Int): PictogramEntity

    suspend fun addPictogramEntity(pictogramEntity: PictogramEntity): Flow<Unit>

    suspend fun addCategoryEntity(categoryEntity: CategoryEntity): Flow<Unit>

    suspend fun updatePictogramEntity(pictogramEntity: PictogramEntity): Flow<Unit>

    suspend fun updateCategoryEntity(categoryEntity: CategoryEntity): Flow<Unit>

    suspend fun updatePictogramPriority(pictogramEntity: PictogramEntity): Flow<Unit>

    fun getMainPictogramId(): Int
    fun setMainPictogramId(pictogramId: Int): Flow<Unit>

    fun getFirstActionPictogramId(): Int
    fun setFirstActionPictogramId(pictogramId: Int): Flow<Unit>

    fun getSecondActionPictogramId(): Int
    fun setSecondActionPictogramId(pictogramId: Int): Flow<Unit>

    fun getAttributePictogramId(): Int
    fun setAttributePictogramId(pictogramId: Int): Flow<Unit>

    fun getSecondPictogramAttributeId(): Int
    fun setSecondAttributePictogramId(pictogramId: Int): Flow<Unit>

    suspend fun updateCategories(categoriesEntities: List<CategoryEntity>)

    suspend fun updatePictograms(pictogramsEntities: List<PictogramEntity>)

    suspend fun deletePictograms(pictogramEntities: List<PictogramEntity>)

    suspend fun deleteCategories(categoriesEntities: List<CategoryEntity>)
}