package com.cmi.presentation.components.chooser.pictogram

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetPictogramsByCategoryUseCase
import com.cmi.presentation.Constants.SHIMMER_EFFECT_DELAY
import com.cmi.presentation.components.chooser.PictureChooserEvent
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.mapper.toPictogramModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PictogramChooserViewModel(
    private val categoryId: Int,
    private val getPictogramsByCategoryUseCase: GetPictogramsByCategoryUseCase
) : ViewModel() {

    val uiState = MutableStateFlow(PictogramChooserState())

    init {
        getPictograms(categoryId)
    }

    fun handleEvent(event: PictureChooserEvent) {
        when(event){
            is PictureChooserEvent.Reload -> {
                getPictograms(categoryId = categoryId, withLoading = false)
            }
        }
    }

    private fun getPictograms(categoryId: Int, withLoading: Boolean = true) = viewModelScope.launch {
        if(withLoading) {
            showLoading(isLoading = true)
            val timeForDelay = uiState.value.pictograms.size
            if (timeForDelay == 0) {
                delay(SHIMMER_EFFECT_DELAY) //Delay for show shimmer effect
            }
        }


        getPictogramsByCategoryUseCase(categoryId)
            .catch {
                if(withLoading) {
                    showLoading(isLoading = false)
                }
            }
            .collect { list ->
                if(withLoading) {
                    showLoading(isLoading = false)
                }
                showPictograms(
                    pictograms = list.map {
                        it.toPictogramModel()
                    }
                )
            }
    }

    private fun showLoading(isLoading: Boolean) {
        uiState.value = uiState.value.copy(isLoading = isLoading)
    }

    private fun showPictograms(pictograms: List<PictogramModel>) {
        uiState.value = uiState.value.copy(pictograms = pictograms)
    }
}