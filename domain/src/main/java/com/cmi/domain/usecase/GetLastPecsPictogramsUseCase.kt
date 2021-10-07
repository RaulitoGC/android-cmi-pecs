package com.cmi.domain.usecase

import com.cmi.domain.system.CmiSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetLastPecsPictogramsUseCase(private val cmiSystem: CmiSystem) {

    companion object{

        private const val PICTOGRAM_WANT_ID = 186

        fun isWantPictogram(pictogramId: Int) = pictogramId == PICTOGRAM_WANT_ID
    }

    suspend operator fun invoke() = flow {
        return@flow cmiSystem.getLastPecsPictograms().collect { pictogramMap ->
            emit(pictogramMap)
        }
    }.flowOn(Dispatchers.IO)

}