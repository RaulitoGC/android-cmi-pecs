package com.cmi.presentation.pecs.pictogram

import com.cmi.presentation.model.PictogramModel

sealed class PecsFlowPictogramSelectionEvent {
    data object GetPictogramModels : PecsFlowPictogramSelectionEvent()
    data object MessageShown : PecsFlowPictogramSelectionEvent()
    data class OnPictogramSelected(val pictogramModel: PictogramModel) :
        PecsFlowPictogramSelectionEvent()
    data class OnPictogramRemoved(val pictogramModel: PictogramModel): PecsFlowPictogramSelectionEvent()

    data object ExecuteSound : PecsFlowPictogramSelectionEvent()
}
