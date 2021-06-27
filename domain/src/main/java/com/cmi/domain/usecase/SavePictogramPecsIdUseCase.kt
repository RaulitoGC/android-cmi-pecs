package com.cmi.domain.usecase

import com.cmi.domain.system.System
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SavePictogramPecsIdUseCase(private val system: System) {

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
        return@flow system.savePictogramPecsId(
            type = type,
            pictogramId = pictogramId
        ).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)
}