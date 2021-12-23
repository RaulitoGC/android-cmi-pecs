package com.cmi.domain.usecase

import com.cmi.domain.entity.Category
import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteCategoriesUseCase(private val localDataSource: LocalDataSource) {

    suspend operator fun invoke(categories: List<Category>) = flow {
        return@flow localDataSource.deleteCategories(categories = categories).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}