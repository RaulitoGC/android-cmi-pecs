package com.cmi.domain.usecase

import com.cmi.domain.entity.Pictogram
import com.cmi.domain.system.System
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdatePictogramsUseCase(private val system: System) {

    suspend operator fun invoke(pictograms: List<Pictogram>) = flow {
        return@flow system.updatePictograms(pictograms = pictograms).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}