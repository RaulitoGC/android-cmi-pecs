package com.cmi.domain.util

import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram
import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource : LocalDataSource{
    companion object {
        const val SIZE_FOR_LIST = 10
        const val INVALID_ID = -1

        var categoryFromDisk = TestDataUtil.getCategory()
        var categoriesFromDisk = TestDataUtil.getCategories(SIZE_FOR_LIST).toMutableList()

        var pictogramFromDisk = TestDataUtil.getPictogram()
        var pictogramsFromDisk = TestDataUtil.getPictograms(SIZE_FOR_LIST).toMutableList()

        var cacheId = HashMap<String, Int>()
        private const val MAIN_ID_KEY = "main_id_key"
        private const val FIRST_ACTION_ID_KEY = "first_action_id_key"
        private const val SECOND_ACTION_ID_KEY = "second_action_id_key"
        private const val ATTRIBUTE_ID_KEY = "attribute_id_key"
        private const val SECOND_PICTOGRAM_ID_KEY = "second_pictogram_id_key"

        fun reset() {
            categoryFromDisk = TestDataUtil.getCategory()
            categoriesFromDisk = TestDataUtil.getCategories(SIZE_FOR_LIST).toMutableList()

            pictogramFromDisk = TestDataUtil.getPictogram()
            pictogramsFromDisk = TestDataUtil.getPictograms(SIZE_FOR_LIST).toMutableList()

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

    override suspend fun getPictograms() = flow {
        emit(pictogramsFromDisk)
    }

    override suspend fun getPictogram(pictogramId: Int): Pictogram =
        pictogramFromDisk.copy(pictogramId)

    override suspend fun addPictogram(pictogram: Pictogram) = flow {
        pictogramsFromDisk.add(pictogram)
        emit(Unit)
    }

    override suspend fun addCategory(category: Category) = flow {
        categoriesFromDisk.add(category)
        emit(Unit)
    }

    override suspend fun updatePictogram(pictogram: Pictogram) = flow {
        val idx = pictogramsFromDisk.indexOfFirst { it.pictogramId == pictogram.pictogramId }
        if (idx != -1) {
            pictogramsFromDisk[idx] = pictogram
        }
        emit(Unit)
    }

    override suspend fun updateCategory(category: Category) = flow {
        val idx = categoriesFromDisk.indexOfFirst { it.categoryId == category.categoryId }
        if (idx != -1) {
            categoriesFromDisk[idx] = category
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

    override fun setFirstActionPictogramId(pictogramId: Int) = flow {
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

    override fun setAttributePictogramId(pictogramId: Int) = flow {
        cacheId[ATTRIBUTE_ID_KEY] = pictogramId
        emit(Unit)
    }

    override fun getSecondPictogramAttributeId(): Int {
        return cacheId.getOrDefault(SECOND_PICTOGRAM_ID_KEY, INVALID_ID)
    }

    override fun setSecondAttributePictogramId(pictogramId: Int) = flow {
        cacheId[SECOND_PICTOGRAM_ID_KEY] = pictogramId
        emit(Unit)
    }

    override suspend fun updateCategories(categories: List<Category>) = flow {
        categories.forEach { category ->
            val idx = categoriesFromDisk.indexOfFirst { it.categoryId == category.categoryId }
            if (idx != -1) {
                categoriesFromDisk[idx] = category
            }
        }
        emit(Unit)
    }

    override suspend fun updatePictograms(pictograms: List<Pictogram>) = flow {
        pictograms.forEach { pictogram ->
            val idx = pictogramsFromDisk.indexOfFirst { it.pictogramId == pictogram.pictogramId }
            if (idx != -1) {
                pictogramsFromDisk[idx] = pictogram
            }
        }
        emit(Unit)
    }

    override suspend fun deletePictograms(pictograms: List<Pictogram>) = flow {
        pictograms.toMutableList().forEach { pictogram ->
            val idx =
                pictogramsFromDisk.indexOfFirst { it.pictogramId == pictogram.pictogramId }
            if (idx != -1 && pictogramsFromDisk.isNotEmpty()) {
                pictogramsFromDisk.removeAt(idx)
            }
        }

        emit(Unit)
    }

    override suspend fun deleteCategories(categories: List<Category>) = flow {
        categories.toMutableList().forEach { category ->
            val idx =
                categoriesFromDisk.indexOfFirst { it.categoryId == category.categoryId }
            if (idx != -1 && categoriesFromDisk.isNotEmpty()) {
                categoriesFromDisk.removeAt(idx)
            }
        }
        emit(Unit)
    }
}