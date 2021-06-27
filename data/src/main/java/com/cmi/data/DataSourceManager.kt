package com.cmi.data

import com.cmi.data.local.LocalDataSource
import com.cmi.data.local.mapper.toCategory
import com.cmi.data.local.mapper.toCategoryEntity
import com.cmi.data.local.mapper.toPictogram
import com.cmi.data.local.mapper.toPictogramEntity
import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram
import com.cmi.domain.system.System
import com.cmi.domain.usecase.SavePictogramPecsIdUseCase.Companion.PICTOGRAM_ATTRIBUTE
import com.cmi.domain.usecase.SavePictogramPecsIdUseCase.Companion.PICTOGRAM_FIRST_ACTION
import com.cmi.domain.usecase.SavePictogramPecsIdUseCase.Companion.PICTOGRAM_INVALID_ID
import com.cmi.domain.usecase.SavePictogramPecsIdUseCase.Companion.PICTOGRAM_MAIN
import com.cmi.domain.usecase.SavePictogramPecsIdUseCase.Companion.PICTOGRAM_SECOND_ACTION
import com.cmi.domain.usecase.SavePictogramPecsIdUseCase.Companion.PICTOGRAM_SECOND_ATTRIBUTE
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class DataSourceManager(private val localDataSource: LocalDataSource) : System {

    override fun getCategories() = flow {
        localDataSource.getCategories()
            .map { categoryEntities ->
                categoryEntities.map { it.toCategory() }
            }.collect { categories ->
                emit(categories)
            }
    }

    override fun getPictogramsByCategory(categoryId: Int) = flow {
        localDataSource.getPictogramsByCategory(categoryId = categoryId)
            .map { pictogramEntities ->
                pictogramEntities.map { it.toPictogram() }
            }.collect { pictograms ->
                emit(pictograms)
            }
    }

    override fun getPictograms() = flow {
        localDataSource.getPictograms()
            .map { pictogramEntities ->
                pictogramEntities.map { it.toPictogram() }
            }.collect { pictograms ->
                emit(pictograms)
            }
    }

    override fun addPictogram(pictogram: Pictogram) = flow {
        localDataSource.addPictogramEntity(pictogramEntity = pictogram.toPictogramEntity())
            .collect { none ->
                emit(none)
            }
    }

    override fun addCategory(category: Category) = flow {
        localDataSource.addCategoryEntity(categoryEntity = category.toCategoryEntity())
            .collect { none ->
                emit(none)
            }
    }

    override fun updatePictogram(pictogram: Pictogram) = flow {
        localDataSource.updatePictogramEntity(pictogramEntity = pictogram.toPictogramEntity())
            .collect { none ->
                emit(none)
            }
    }

    override fun updateCategory(category: Category) = flow {
        localDataSource.updateCategoryEntity(categoryEntity = category.toCategoryEntity())
            .collect { none ->
                emit(none)
            }
    }

    override fun updatePictogramPriority(pictogram: Pictogram) = flow {
        localDataSource.updatePictogramPriority(pictogramEntity = pictogram.toPictogramEntity())
            .collect { none ->
                emit(none)
            }
    }

    override fun savePictogramPecsId(type: String, pictogramId: Int) = flow {
        when (type) {
            PICTOGRAM_MAIN -> {
                emit(localDataSource.setMainPictogramId(pictogramId).collect())
            }

            PICTOGRAM_FIRST_ACTION -> {
                emit(localDataSource.setFirstActionPictogramId(pictogramId).collect())
            }

            PICTOGRAM_SECOND_ACTION -> {
                emit(localDataSource.setSecondActionPictogramId(pictogramId).collect())
            }

            PICTOGRAM_ATTRIBUTE -> {
                emit(localDataSource.setAttributePictogramId(pictogramId).collect())
            }

            PICTOGRAM_SECOND_ATTRIBUTE -> {
                emit(localDataSource.setSecondAttributePictogramId(pictogramId).collect())
            }
        }
    }

    override fun getLastPecsPictograms() = flow<Map<String, Pictogram>> {

        val map = HashMap<String, Pictogram>()
        val mainPictogramId = localDataSource.getMainPictogramId()
        val firstActionPictogramId = localDataSource.getFirstActionPictogramId()
        val secondActionPictogramId = localDataSource.getSecondActionPictogramId()
        val attributePictogramId = localDataSource.getAttributePictogramId()
        val secondAttributePictogramId = localDataSource.getSecondPictogramAttributeId()

        if (firstActionPictogramId != PICTOGRAM_INVALID_ID) {
            map[PICTOGRAM_FIRST_ACTION] =
                localDataSource.getPictogram(firstActionPictogramId).toPictogram()
        }

        if (secondActionPictogramId != PICTOGRAM_INVALID_ID) {
            map[PICTOGRAM_SECOND_ACTION] =
                localDataSource.getPictogram(secondActionPictogramId).toPictogram()
        }

        if (mainPictogramId != PICTOGRAM_INVALID_ID) {
            map[PICTOGRAM_MAIN] = localDataSource.getPictogram(mainPictogramId).toPictogram()
        }

        if (attributePictogramId != PICTOGRAM_INVALID_ID) {
            map[PICTOGRAM_ATTRIBUTE] =
                localDataSource.getPictogram(attributePictogramId).toPictogram()
        }

        if (secondAttributePictogramId != PICTOGRAM_INVALID_ID) {
            map[PICTOGRAM_SECOND_ATTRIBUTE] =
                localDataSource.getPictogram(secondAttributePictogramId).toPictogram()
        }

        emit(map)
    }

    override fun cleanLastPecsPictograms() = flow {
        localDataSource.setMainPictogramId(PICTOGRAM_INVALID_ID).collect()
        localDataSource.setFirstActionPictogramId(PICTOGRAM_INVALID_ID).collect()
        localDataSource.setSecondActionPictogramId(PICTOGRAM_INVALID_ID).collect()
        localDataSource.setAttributePictogramId(PICTOGRAM_INVALID_ID).collect()
        emit(localDataSource.setSecondAttributePictogramId(PICTOGRAM_INVALID_ID).collect())
    }

    override fun updateCategories(categories: List<Category>) = flow {
        emit(localDataSource.updateCategories(categoriesEntities = categories.map { it.toCategoryEntity() }))
    }

    override fun updatePictograms(pictograms: List<Pictogram>) = flow {
        emit(localDataSource.updatePictograms(pictogramsEntities = pictograms.map { it.toPictogramEntity() }))
    }

    override fun deletePictograms(pictograms: List<Pictogram>) = flow {
        emit(localDataSource.deletePictograms(pictogramEntities = pictograms.map { it.toPictogramEntity() }))
    }

    override fun deleteCategories(categories: List<Category>) = flow {
        emit(localDataSource.deleteCategories(categoriesEntities = categories.map { it.toCategoryEntity() }))
    }
}