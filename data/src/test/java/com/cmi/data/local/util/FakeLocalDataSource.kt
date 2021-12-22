package com.cmi.data.local.util

import com.cmi.data.local.LocalDataSource
import com.cmi.data.local.database.entity.CategoryEntity
import com.cmi.data.local.database.entity.PictogramEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource : LocalDataSource {

    companion object {
        const val SIZE_FOR_LIST = 10
        const val INVALID_ID = -1

        var categoryFromDisk = TestDataUtil.getCategoryEntity()
        var categoriesFromDisk = TestDataUtil.getCategoriesEntities(SIZE_FOR_LIST).toMutableList()

        var pictogramFromDisk = TestDataUtil.getPictogramEntity()
        var pictogramsFromDisk = TestDataUtil.getPictogramEntities(SIZE_FOR_LIST).toMutableList()

        var cacheId = HashMap<String, Int>()
        private const val MAIN_ID_KEY = "main_id_key"
        private const val FIRST_ACTION_ID_KEY = "first_action_id_key"
        private const val SECOND_ACTION_ID_KEY = "second_action_id_key"
        private const val ATTRIBUTE_ID_KEY = "attribute_id_key"
        private const val SECOND_PICTOGRAM_ID_KEY = "second_pictogram_id_key"

        fun reset() {
            categoryFromDisk = TestDataUtil.getCategoryEntity()
            categoriesFromDisk = TestDataUtil.getCategoriesEntities(SIZE_FOR_LIST).toMutableList()

            pictogramFromDisk = TestDataUtil.getPictogramEntity()
            pictogramsFromDisk = TestDataUtil.getPictogramEntities(SIZE_FOR_LIST).toMutableList()

            cacheId = HashMap<String, Int>()
        }
    }

    override suspend fun getCategories() = flow {
        emit(categoriesFromDisk)
    }

    override suspend fun getPictogramsByCategory(categoryId: Int) = flow {
        emit(pictogramsFromDisk.map {
            it.copy(categoryId = categoryId)
        })
    }

    override suspend fun getPictograms() = flow{
        emit(pictogramsFromDisk)
    }

    override suspend fun getPictogram(pictogramId: Int): PictogramEntity = pictogramFromDisk.copy(pictogramId)

    override suspend fun addPictogramEntity(pictogramEntity: PictogramEntity) = flow {
        pictogramsFromDisk.add(pictogramEntity)
        emit(Unit)
    }

    override suspend fun addCategoryEntity(categoryEntity: CategoryEntity) = flow {
        categoriesFromDisk.add(categoryEntity)
        emit(Unit)
    }

    override suspend fun updatePictogramEntity(pictogramEntity: PictogramEntity) = flow {
        val idx = pictogramsFromDisk.indexOfFirst { it.pictogramId == pictogramEntity.pictogramId}
        if(idx != -1) {
           pictogramsFromDisk[idx] = pictogramEntity
        }
        emit(Unit)
    }

    override suspend fun updateCategoryEntity(categoryEntity: CategoryEntity) = flow{
        val idx = categoriesFromDisk.indexOfFirst { it.categoryId == categoryEntity.categoryId}
        if(idx != -1) {
            categoriesFromDisk[idx] = categoryEntity
        }
        emit(Unit)
    }

    override suspend fun updatePictogramPriority(pictogramEntity: PictogramEntity) = flow {
        val idx = pictogramsFromDisk.indexOfFirst { it.pictogramId == pictogramEntity.pictogramId}
        if(idx != -1) {
            val newPriority = (pictogramEntity.priority ?: 0).plus(1)
            pictogramsFromDisk[idx] = pictogramEntity.copy(priority = newPriority)
        }
        emit(Unit)
    }

    override fun getMainPictogramId(): Int {
        return cacheId.getOrDefault(MAIN_ID_KEY, INVALID_ID)
    }

    override fun setMainPictogramId(pictogramId: Int) = flow {
        cacheId[MAIN_ID_KEY] = pictogramId
        emit(Unit)
    }

    override fun getFirstActionPictogramId(): Int {
        return cacheId.getOrDefault(FIRST_ACTION_ID_KEY, INVALID_ID)

    }

    override fun setFirstActionPictogramId(pictogramId: Int) = flow{
        cacheId[FIRST_ACTION_ID_KEY] = pictogramId
        emit(Unit)
    }

    override fun getSecondActionPictogramId(): Int {
        return cacheId.getOrDefault(SECOND_ACTION_ID_KEY, INVALID_ID)

    }

    override fun setSecondActionPictogramId(pictogramId: Int) = flow {
        cacheId[SECOND_ACTION_ID_KEY] = pictogramId
        emit(Unit)
    }

    override fun getAttributePictogramId(): Int {
        return cacheId.getOrDefault(ATTRIBUTE_ID_KEY, INVALID_ID)
    }

    override fun setAttributePictogramId(pictogramId: Int) = flow{
        cacheId[ATTRIBUTE_ID_KEY] = pictogramId
        emit(Unit)
    }

    override fun getSecondPictogramAttributeId(): Int {
        return cacheId.getOrDefault(SECOND_PICTOGRAM_ID_KEY, INVALID_ID)
    }

    override fun setSecondAttributePictogramId(pictogramId: Int) = flow{
        cacheId[SECOND_PICTOGRAM_ID_KEY] = pictogramId
        emit(Unit)
    }

    override suspend fun updateCategories(categoriesEntities: List<CategoryEntity>) {
        categoriesEntities.forEach { categoryEntity ->
            val idx = categoriesFromDisk.indexOfFirst { it.categoryId == categoryEntity.categoryId }
            if(idx != -1) {
                categoriesFromDisk[idx] = categoryEntity
            }
        }
    }

    override suspend fun updatePictograms(pictogramsEntities: List<PictogramEntity>) {
        pictogramsEntities.forEach { pictogramEntity ->
            val idx = pictogramsFromDisk.indexOfFirst { it.pictogramId == pictogramEntity.pictogramId }
            if(idx != -1) {
                pictogramsFromDisk[idx] = pictogramEntity
            }
        }
    }

    override suspend fun deletePictograms(pictogramEntities: List<PictogramEntity>) {
        pictogramEntities.toMutableList().forEach { pictogramEntity ->
            val idx =
                pictogramsFromDisk.indexOfFirst { it.pictogramId == pictogramEntity.pictogramId }
            if (idx != -1 && pictogramsFromDisk.isNotEmpty()) {
                pictogramsFromDisk.removeAt(idx)
            }
        }
    }

    override suspend fun deleteCategories(categoriesEntities: List<CategoryEntity>) {
        categoriesEntities.toMutableList().forEach { categoryEntity ->
            val idx =
                categoriesFromDisk.indexOfFirst { it.categoryId == categoryEntity.categoryId }
            if (idx != -1 && categoriesFromDisk.isNotEmpty()) {
                categoriesFromDisk.removeAt(idx)
            }
        }
    }
}
