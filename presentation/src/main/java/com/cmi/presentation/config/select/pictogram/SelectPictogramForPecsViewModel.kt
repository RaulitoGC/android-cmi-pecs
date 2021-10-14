package com.cmi.presentation.config.select.pictogram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetPictogramsByCategoryUseCase
import com.cmi.domain.usecase.UpdatePictogramsUseCase
import com.cmi.presentation.Constants
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictogramSelectableModel
import com.cmi.presentation.model.mapper.toPictogram
import com.cmi.presentation.model.mapper.toPictogramModel
import com.cmi.presentation.model.mapper.toPictogramSelectableModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SelectPictogramForPecsViewModel @Inject constructor(
    private val getPictogramsByCategoryUseCase: GetPictogramsByCategoryUseCase,
    private val updatePictogramsUseCase: UpdatePictogramsUseCase
) : ViewModel() {

    private val _pictograms: MutableLiveData<List<PictogramSelectableModel>> by lazy {
        MutableLiveData<List<PictogramSelectableModel>>()
    }
    val pictograms: LiveData<List<PictogramSelectableModel>> = _pictograms

    private val _uiState: MutableLiveData<SelectPictogramUiState> by lazy {
        MutableLiveData<SelectPictogramUiState>()
    }
    val uiState: LiveData<SelectPictogramUiState> = _uiState

    fun getPictogramsByCategory(categoryModel: CategoryModel) = viewModelScope.launch {
        delay(Constants.SHIMMER_EFFECT_DELAY) //Delay for show shimmer effect
        getPictogramsByCategoryUseCase(categoryId = categoryModel.categoryId ?: 0)
            .catch { throwable ->
                Timber.e(throwable)
            }
            .collect { pictograms ->
                _pictograms.value =
                    pictograms.map { it.toPictogramModel().toPictogramSelectableModel() }
            }
    }

    fun updatePictogramsForPecs(pictogramsSelected: List<PictogramSelectableModel>) = viewModelScope.launch {
        if (pictogramsSelected.isNotEmpty()) {
            val pictograms = pictogramsSelected.map { it.toPictogramModel().toPictogram() }
            updatePictogramsUseCase(pictograms = pictograms)
                .catch { throwable ->
                    Timber.e(throwable)
                    emitUiState(showError = Any())
                }.collect{
                    emitUiState(showSuccess = Any())
                }
        }
    }

    private fun emitUiState(showSuccess: Any? = null, showError: Any? = null) {
        _uiState.value = SelectPictogramUiState(
            showSuccess = showSuccess,
            showError = showError
        )
    }
}

data class SelectPictogramUiState(
    val showSuccess: Any? = null,
    val showError: Any? = null
)