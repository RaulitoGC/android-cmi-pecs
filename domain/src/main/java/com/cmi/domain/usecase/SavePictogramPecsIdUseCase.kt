package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SavePictogramPecsIdUseCase(private val localDataSource: LocalDataSource) {

    companion object {
        const val PICTOGRAM_MAIN = "com.cmi.domain.usecase.PICTOGRAM_MAIN_ID"
        const val PICTOGRAM_FIRST_ACTION =
            "com.cmi.data.domain.usecase.PICTOGRAM_FIRST_ACTION_ID"
        const val PICTOGRAM_SECOND_ACTION =
            "com.cmi.data.domain.usecase.PICTOGRAM_SECOND_ACTION_ID"
        const val PICTOGRAM_ATTRIBUTE =
            "com.cmi.data.domain.usecase.PICTOGRAM_ATTRIBUTE_ID"
        const val PICTOGRAM_SECOND_ATTRIBUTE = "com.cmi.data.domain.usecase.PICTOGRAM_SECOND_ATTRIBUTE_ID"

        const val PICTOGRAM_INVALID_ID = -1
    }

    suspend operator fun invoke(type: String, pictogramId: Int) = flow {
        return@flow when (type) {
            PICTOGRAM_MAIN -> {
                emit(localDataSource.setMainPictogramId(pictogramId).collect())
            }

            PICTOGRAM_FIRST_ACTION -> {
                emit(localDataSource.setFirstActionPictogramId(pictogramId).collect())
            }

            PICTOGRAM_SECOND_ACTION -> {
                emit(localDataSource.setSecondActionPictogramId(pictogramId).collect())
            }

            PICTOGRAM_ATTRIBUTE -> {
                emit(localDataSource.setAttributePictogramId(pictogramId).collect())
            }

            PICTOGRAM_SECOND_ATTRIBUTE -> {
                emit(localDataSource.setSecondAttributePictogramId(pictogramId).collect())
            }
            else -> {
                emit(Unit)
            }
        }
    }.flowOn(Dispatchers.IO)
}