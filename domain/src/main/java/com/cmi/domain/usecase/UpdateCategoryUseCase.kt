package com.cmi.domain.usecase

import com.cmi.domain.entity.Category
import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdateCategoryUseCase(private val localDataSource: LocalDataSource) {

    suspend operator fun invoke(category: Category) = flow {
        return@flow localDataSource.updateCategory(category = category).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}