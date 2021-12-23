package com.cmi.domain.usecase

import com.cmi.domain.entity.Pictogram
import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdatePictogramPriorityUseCase(private val localDataSource: LocalDataSource) {

    suspend operator fun invoke(pictogram: Pictogram) = flow {
        val newPriority = pictogram.priority ?: 0
        return@flow localDataSource
            .updatePictogram(pictogram = pictogram.copy(priority = newPriority + 1))
            .collect { none ->
                emit(none)
            }
    }.flowOn(Dispatchers.IO)

}