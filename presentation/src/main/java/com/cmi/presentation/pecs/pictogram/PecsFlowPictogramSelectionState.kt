package com.cmi.presentation.pecs.pictogram

import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.model.PictogramModel

data class PecsFlowPictogramSelectionState(
    val pictograms: List<PictogramModel> = emptyList(),
    val pictogramsForPecs: List<PictogramModel>,
    val cardViewConfig: CardViewConfig = CardViewConfig(),
    val showMessage: String? = null
)