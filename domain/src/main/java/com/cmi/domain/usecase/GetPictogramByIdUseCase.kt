package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetPictogramByIdUseCase(private val localDataSource: LocalDataSource) {

    suspend operator fun invoke(categoryId: Int) = flow {
        return@flow localDataSource.getCategoryById(categoryId).collect { category ->
            emit(category)
        }
    }.flowOn(Dispatchers.IO)
}
