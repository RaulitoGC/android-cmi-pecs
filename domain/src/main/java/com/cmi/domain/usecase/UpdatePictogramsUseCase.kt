package com.cmi.domain.usecase

import com.cmi.domain.entity.Pictogram
import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdatePictogramsUseCase(private val localDataSource: LocalDataSource) {

    suspend operator fun invoke(pictograms: List<Pictogram>) = flow {
        return@flow localDataSource.updatePictograms(pictograms = pictograms).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}