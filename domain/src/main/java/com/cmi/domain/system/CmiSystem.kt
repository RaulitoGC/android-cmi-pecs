package com.cmi.domain.system

import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Pictogram
import kotlinx.coroutines.flow.Flow

interface CmiSystem {

    fun getCategories(): Flow<List<Category>>

    fun getPictogramsByCategory(categoryId: Int): Flow<List<Pictogram>>

    fun getPictograms(): Flow<List<Pictogram>>

    fun addPictogram(pictogram: Pictogram): Flow<Unit>

    fun addCategory(category: Category): Flow<Unit>

    fun updatePictogram(pictogram: Pictogram): Flow<Unit>

    fun updateCategory(category: Category): Flow<Unit>

    fun updatePictogramPriority(pictogram: Pictogram): Flow<Unit>

    fun savePictogramPecsId(type: String, pictogramId: Int): Flow<Unit>

    fun getLastPecsPictograms(): Flow<Map<String, Pictogram>>

    fun cleanLastPecsPictograms(): Flow<Unit>

    fun updateCategories(categories: List<Category>): Flow<Unit>

    fun updatePictograms(pictograms: List<Pictogram>): Flow<Unit>

    fun deletePictograms(pictograms: List<Pictogram>): Flow<Unit>

    fun deleteCategories(categories: List<Category>) : Flow<Unit>
}