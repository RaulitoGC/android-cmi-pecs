package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetPictogramsByCategoryUseCase(private val localDataSource: LocalDataSource) {

    companion object{
        private const val ATTRIBUTE_CATEGORY_ID = 2
        private const val ACTION_CATEGORY_ID = 18

        fun isAttribute(categoryId: Int?): Boolean{
            return categoryId == ATTRIBUTE_CATEGORY_ID
        }

        fun isAction(categoryId: Int?): Boolean{
            return categoryId == ACTION_CATEGORY_ID
        }
    }

    suspend operator fun invoke(categoryId: Int) = flow {
        return@flow localDataSource.getPictogramsByCategory(categoryId = categoryId).collect { pictograms ->
            emit(pictograms)
        }
    }.flowOn(Dispatchers.IO)

}