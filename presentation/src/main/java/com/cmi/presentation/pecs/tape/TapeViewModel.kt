package com.cmi.presentation.pecs.tape

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetLastPecsPictogramsUseCase
import com.cmi.domain.usecase.SavePictogramPecsIdUseCase
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.mapper.toPictogramModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TapeViewModel(
    private val getLastPecsPictogramsUseCase: GetLastPecsPictogramsUseCase
) : ViewModel() {

    private val _uiState: MutableLiveData<TapeUiState> by lazy {
        MutableLiveData<TapeUiState>()
    }
    val uiState: LiveData<TapeUiState> = _uiState

    init {
        loadInformation()
    }

    private fun loadInformation() = viewModelScope.launch {
        getLastPecsPictogramsUseCase()
            .collect { mapPecs ->
                emitUiState(
                    mainPictogramModel = mapPecs[SavePictogramPecsIdUseCase.PICTOGRAM_MAIN]?.toPictogramModel(),
                    actionFirstPictogramModel = mapPecs[SavePictogramPecsIdUseCase.PICTOGRAM_FIRST_ACTION]?.toPictogramModel(),
                    actionSecondPictogramModel = mapPecs[SavePictogramPecsIdUseCase.PICTOGRAM_SECOND_ACTION]?.toPictogramModel(),
                    attributeFirstPictogramModel = mapPecs[SavePictogramPecsIdUseCase.PICTOGRAM_ATTRIBUTE]?.toPictogramModel(),
                    attributeSecondPictogramModel = mapPecs[SavePictogramPecsIdUseCase.PICTOGRAM_SECOND_ATTRIBUTE]?.toPictogramModel(),
                    launchSound = Any()
                )
            }
    }

    private fun emitUiState(
        mainPictogramModel: PictogramModel? = null,
        actionFirstPictogramModel: PictogramModel? = null,
        actionSecondPictogramModel: PictogramModel? = null,
        attributeFirstPictogramModel: PictogramModel? = null,
        attributeSecondPictogramModel: PictogramModel? = null,
        launchSound: Any? = null
    ) {
        _uiState.value = TapeUiState(
            mainPictogramModel = mainPictogramModel,
            actionFirstPictogramModel = actionFirstPictogramModel,
            actionSecondPictogramModel = actionSecondPictogramModel,
            attributeFirstPictogramModel = attributeFirstPictogramModel,
            attributeSecondPictogramModel = attributeSecondPictogramModel,
            launchSound = launchSound
        )
    }
}

data class TapeUiState(
    val mainPictogramModel: PictogramModel? = null,
    val actionFirstPictogramModel: PictogramModel? = null,
    val actionSecondPictogramModel: PictogramModel? = null,
    val attributeFirstPictogramModel: PictogramModel? = null,
    val attributeSecondPictogramModel: PictogramModel? = null,
    val launchSound: Any? = null
)