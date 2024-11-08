package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetPictogramByIdUseCase(private val localDataSource: LocalDataSource) {

    suspend operator fun invoke(pictogramId: Int) = flow {
        return@flow localDataSource.getPictogramById(pictogramId).collect { pictogram ->
            emit(pictogram)
        }
    }.flowOn(Dispatchers.IO)
}
