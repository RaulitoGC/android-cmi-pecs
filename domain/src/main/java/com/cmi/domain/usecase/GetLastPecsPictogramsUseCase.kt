package com.cmi.domain.usecase

import com.cmi.domain.entity.Pictogram
import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetLastPecsPictogramsUseCase(private val localDataSource: LocalDataSource) {

    companion object{

        private const val PICTOGRAM_WANT_ID = 186

        fun isWantPictogram(pictogramId: Int) = pictogramId == PICTOGRAM_WANT_ID
    }

    suspend operator fun invoke() = flow {
        val map = HashMap<String, Pictogram>()
        val mainPictogramId = localDataSource.getMainPictogramId()
        val firstActionPictogramId = localDataSource.getFirstActionPictogramId()
        val secondActionPictogramId = localDataSource.getSecondActionPictogramId()
        val attributePictogramId = localDataSource.getAttributePictogramId()
        val secondAttributePictogramId = localDataSource.getSecondPictogramAttributeId()

        if (firstActionPictogramId != SavePictogramPecsIdUseCase.PICTOGRAM_INVALID_ID) {
            map[SavePictogramPecsIdUseCase.PICTOGRAM_FIRST_ACTION] = localDataSource.getPictogram(firstActionPictogramId)
        }

        if (secondActionPictogramId != SavePictogramPecsIdUseCase.PICTOGRAM_INVALID_ID) {
            map[SavePictogramPecsIdUseCase.PICTOGRAM_SECOND_ACTION] = localDataSource.getPictogram(secondActionPictogramId)
        }

        if (mainPictogramId != SavePictogramPecsIdUseCase.PICTOGRAM_INVALID_ID) {
            map[SavePictogramPecsIdUseCase.PICTOGRAM_MAIN] = localDataSource.getPictogram(mainPictogramId)
        }

        if (attributePictogramId != SavePictogramPecsIdUseCase.PICTOGRAM_INVALID_ID) {
            map[SavePictogramPecsIdUseCase.PICTOGRAM_ATTRIBUTE] = localDataSource.getPictogram(attributePictogramId)
        }

        if (secondAttributePictogramId != SavePictogramPecsIdUseCase.PICTOGRAM_INVALID_ID) {
            map[SavePictogramPecsIdUseCase.PICTOGRAM_SECOND_ATTRIBUTE] = localDataSource.getPictogram(secondAttributePictogramId)
        }

        return@flow emit(map)
    }.flowOn(Dispatchers.IO)

}