package com.cmi.domain.usecase

import com.cmi.domain.entity.Pictogram
import com.cmi.domain.system.System
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdatePictogramPriorityUseCase(private val system: System) {

    suspend operator fun invoke(pictogram: Pictogram) = flow {
        return@flow system.updatePictogramPriority(pictogram = pictogram).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}