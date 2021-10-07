package com.cmi.domain.usecase

import com.cmi.domain.entity.Pictogram
import com.cmi.domain.system.CmiSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeletePictogramsUseCase(private val cmiSystem: CmiSystem) {

    suspend operator fun invoke(pictograms: List<Pictogram>) = flow {
        return@flow cmiSystem.deletePictograms(pictograms = pictograms).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}