package com.cmi.presentation.components.chooser.pictogram

import com.cmi.presentation.Constants.DEFAULT_PICTURE_SIZE_SHIMMER
import com.cmi.presentation.model.PictogramModel

data class PictogramChooserState(
    val isLoading: Boolean = false,
    val pictograms: List<PictogramModel> = emptyList()
) {

    fun getPictogramsSize() = pictograms.size.takeIf { it > 0} ?: DEFAULT_PICTURE_SIZE_SHIMMER
}