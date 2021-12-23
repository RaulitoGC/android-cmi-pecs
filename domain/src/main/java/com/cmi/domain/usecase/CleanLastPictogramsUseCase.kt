package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CleanLastPictogramsUseCase(private val localDataSource: LocalDataSource) {

    suspend operator fun invoke() = flow {
        localDataSource.apply {
            setMainPictogramId(SavePictogramPecsIdUseCase.PICTOGRAM_INVALID_ID).collect()
            setFirstActionPictogramId(SavePictogramPecsIdUseCase.PICTOGRAM_INVALID_ID).collect()
            setSecondActionPictogramId(SavePictogramPecsIdUseCase.PICTOGRAM_INVALID_ID).collect()
            setAttributePictogramId(SavePictogramPecsIdUseCase.PICTOGRAM_INVALID_ID).collect()
            setSecondAttributePictogramId(SavePictogramPecsIdUseCase.PICTOGRAM_INVALID_ID).collect()
        }
        return@flow emit(Unit)
    }.flowOn(Dispatchers.IO)

}