package com.cmi.presentation.pecs.pictogram

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetLastPecsPictogramsUseCase
import com.cmi.domain.usecase.GetPictogramsByCategoryUseCase
import com.cmi.domain.usecase.SavePictogramPecsIdUseCase
import com.cmi.domain.usecase.UpdatePictogramPriorityUseCase
import com.cmi.presentation.R
import com.cmi.presentation.manager.StringResourceManager
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.mapper.toPictogram
import com.cmi.presentation.model.mapper.toPictogramModel
import com.cmi.presentation.pecs.PecsFlowContentHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class PecsFlowPictogramSelectionViewModel(
    categoryId: Int,
    private val stringResourceManager: StringResourceManager,
    private val getPictogramsByCategoryUseCase: GetPictogramsByCategoryUseCase,
    private val updatePictogramPriorityUseCase: UpdatePictogramPriorityUseCase,
    private val pecsFlowContentHolder: PecsFlowContentHolder
) : ViewModel() {

    init {
        getPictogramByCategoryId(categoryId)
    }

    val uiState = MutableStateFlow(
        PecsFlowPictogramSelectionState(
            pictogramsForPecs = pecsFlowContentHolder.getPictureModels()
        )
    )

    fun handleEvent(event: PecsFlowPictogramSelectionEvent) {
        when (event) {
            is PecsFlowPictogramSelectionEvent.MessageShown -> {
                uiState.value = uiState.value.copy(showMessage = null)
            }

            is PecsFlowPictogramSelectionEvent.OnPictogramSelected -> {
                val pictogramSelected = event.pictogramModel
                updatePictogramPriority(pictogramSelected)
                savePictogramModelInContentHolder(pictogramSelected)
            }

            is PecsFlowPictogramSelectionEvent.OnPictogramRemoved -> {
                val pictogramSelected = event.pictogramModel
                removePictogramModelInContentHolder(pictogramSelected)
            }

            PecsFlowPictogramSelectionEvent.GetPictogramModels -> {
                uiState.value = uiState.value.copy(pictograms = pecsFlowContentHolder.getPictureModels())
            }

            is PecsFlowPictogramSelectionEvent.ExecuteSound -> {

            }
        }
    }

    private fun getPictogramByCategoryId(categoryId: Int) = viewModelScope.launch {
        getPictogramsByCategoryUseCase(categoryId)
            .catch { throwable ->
                showMessage(R.string.text_generic_error)
                Timber.e(throwable)
            }.collect { pictograms ->
                val pictogramsToUpdate = pictograms.filter {
                    it.isSelectedForPecs == true
                }.map {
                    it.toPictogramModel()
                }
                updatePictograms(pictogramsToUpdate)
            }
    }

    private fun updatePictogramPriority(
        pictogramModel: PictogramModel
    ) = viewModelScope.launch {
        updatePictogramPriorityUseCase(pictogram = pictogramModel.toPictogram()).collect()
    }

    private fun savePictogramModelInContentHolder(pictogramModel: PictogramModel) = viewModelScope.launch {
        pecsFlowContentHolder.save(pictogramModel){ pictogramsModel ->
            updatePictogramForPecs(pictogramsModel)
        }
    }

    private fun removePictogramModelInContentHolder(pictogramModel: PictogramModel) = viewModelScope.launch {
        pecsFlowContentHolder.remove(pictogramModel){ pictogramsModel ->
            updatePictogramForPecs(pictogramsModel)
        }
    }

    private fun updatePictograms(pictograms: List<PictogramModel>) {
        uiState.value = uiState.value.copy(pictograms = pictograms)
    }

    private fun updatePictogramForPecs( pictograms: List<PictogramModel>){
        uiState.value = uiState.value.copy(pictogramsForPecs = pictograms.toList())
    }

    private fun showMessage(
        @StringRes message: Int
    ) {
        uiState.value = uiState.value.copy(showMessage = stringResourceManager.getString(message))
    }
}