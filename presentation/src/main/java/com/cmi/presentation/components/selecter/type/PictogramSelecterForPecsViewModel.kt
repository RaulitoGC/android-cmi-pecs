package com.cmi.presentation.components.selecter.type

import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetPictogramsByCategoryUseCase
import com.cmi.domain.usecase.UpdatePictogramsUseCase
import com.cmi.presentation.Constants.SHIMMER_EFFECT_DELAY
import com.cmi.presentation.R
import com.cmi.presentation.components.selecter.PictureSelecterForPecsViewModel
import com.cmi.presentation.manager.StringResourceManager
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.model.mapper.toPictogram
import com.cmi.presentation.model.mapper.toPictogramModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class PictogramSelecterForPecsViewModel(
    categoryId: Int,
    private val stringResourceManager: StringResourceManager,
    private val getPictogramsByCategoryUseCase: GetPictogramsByCategoryUseCase,
    private val updatePictogramsUseCase: UpdatePictogramsUseCase
) : PictureSelecterForPecsViewModel(stringResourceManager) {

    init {
        getPictogramsByCategory(categoryId)
    }

    private fun getPictogramsByCategory(categoryId: Int) = viewModelScope.launch {
        showLoading(isLoading = true)
        val timeForDelay = uiState.value.pictureModels.size
        if (timeForDelay == 0) {
            delay(SHIMMER_EFFECT_DELAY)
        }

        getPictogramsByCategoryUseCase(categoryId = categoryId)
            .catch { throwable ->
                Timber.e(throwable)
                showLoading(isLoading = false)
                showErrorMessage()
            }.collect { list ->
                showLoading(isLoading = false)
                showPictures(
                    pictureModels = list.map {
                        val pictogramModel = it.toPictogramModel()
                        pictogramModel.copy(
                            isSelectedForUiEnabled = true,
                            isSelected = pictogramModel.isSelectedForPecs
                        )
                    }
                )
            }
    }

    override fun getSuccessMessage(): String {
        return stringResourceManager.getString(R.string.text_select_pictograms_success)
    }

    override fun onUpdatePicturesSelected(pictureModels: List<PictureModel>) {
        viewModelScope.launch {
            if (pictureModels.isNotEmpty()) {
                val pictograms = pictureModels.mapNotNull {
                    (it as? PictogramModel)?.toPictogram()
                }
                updatePictogramsUseCase(pictograms = pictograms)
                    .catch { throwable ->
                        Timber.e(throwable)
                        showErrorMessage()
                    }.collect {
                        showSuccessMessage()
                    }
            }
        }
    }

}