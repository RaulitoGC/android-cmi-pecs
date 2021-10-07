package com.cmi.domain.usecase

import com.cmi.domain.entity.Pictogram
import com.cmi.domain.system.CmiSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddPictogramUseCase(private val cmiSystem: CmiSystem) {

    suspend operator fun invoke(pictogram: Pictogram) = flow {
        return@flow cmiSystem.addPictogram(pictogram = pictogram).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}