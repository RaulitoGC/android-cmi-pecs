package com.cmi.presentation.components.selecter

import com.cmi.presentation.R
import com.cmi.presentation.config.add.model.SelectableTitleConfig
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.model.PictureModel

data class PictureSelecterForPecsState(
    val isLoading: Boolean = false,
    val pictureModels: List<PictureModel> = emptyList(),
    val titleConfig: SelectableTitleConfig = SelectableTitleConfig(
        title = "",
        isActionEnabled = false
    ),
    val showSuccessToastMessage: String? = null,
    val showErrorToastMessage: String? = null,
    val cardViewConfig: CardViewConfig = CardViewConfig()
) {
    fun getPicturesSize() = pictureModels.size.takeIf { it > 0} ?: DEFAULT_CATEGORY_SIZE_SHIMMER

    companion object {
        private const val DEFAULT_CATEGORY_SIZE_SHIMMER = 20
    }
}
