package com.cmi.presentation.config.edit.pictogram.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetPictogramsByCategoryUseCase
import com.cmi.presentation.Constants
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.mapper.toPictogramModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class SelectPictogramViewModel(
    private val categoryModel: CategoryModel,
    private val getPictogramsByCategoryUseCase: GetPictogramsByCategoryUseCase
) : ViewModel() {

    private val _pictograms: MutableLiveData<List<PictogramModel>> by lazy {
        MutableLiveData<List<PictogramModel>>().also {
            it.value = emptyList()
        }
    }
    val pictograms: LiveData<List<PictogramModel>> = _pictograms

    fun getPictogramsByCategory() = viewModelScope.launch {

        val timeForDelay = pictograms.value?.size ?: 0
        if(timeForDelay == 0){
            delay(Constants.SHIMMER_EFFECT_DELAY) //Delay for show shimmer effect
        }

        getPictogramsByCategoryUseCase(categoryId = categoryModel.categoryId ?: 0)
            .catch { exception ->
                Timber.e(exception)
            }
            .collect { pictograms ->
                _pictograms.value = pictograms.map { it.toPictogramModel() }
            }
    }
}