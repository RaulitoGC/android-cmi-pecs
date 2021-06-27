package com.cmi.domain.usecase

import com.cmi.domain.system.System
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CleanLastPictogramsUseCase(private val system: System) {

    suspend operator fun invoke() = flow {
        return@flow system.cleanLastPecsPictograms().collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}