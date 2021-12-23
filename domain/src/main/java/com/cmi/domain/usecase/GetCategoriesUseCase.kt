package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCategoriesUseCase(private val localDataSource: LocalDataSource) {

    suspend operator fun invoke() = flow {
        return@flow localDataSource.getCategories().collect { categories ->
            emit(categories)
        }
    }.flowOn(Dispatchers.IO)

}