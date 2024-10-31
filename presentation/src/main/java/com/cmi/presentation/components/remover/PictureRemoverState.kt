package com.cmi.presentation.components.remover

import com.cmi.presentation.Constants.DEFAULT_PICTURE_SIZE_SHIMMER
import com.cmi.presentation.config.add.model.SelectableTitleConfig
import com.cmi.presentation.model.PictureModel

data class PictureRemoverState(
    val showAlertMessage: String? = null,
    val showToastMessage: String? = null,
    val description: String? = null,
    val isLoading: Boolean = false,
    val titleConfig: SelectableTitleConfig = SelectableTitleConfig(
        title = "",
        isActionEnabled = false
    ),
    val pictureModels: List<PictureModel> = emptyList()
){

    fun getPictureSize() = pictureModels.size.takeIf { it > 0} ?: DEFAULT_PICTURE_SIZE_SHIMMER
}
