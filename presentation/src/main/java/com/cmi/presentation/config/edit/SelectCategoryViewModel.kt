package com.cmi.presentation.config.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.presentation.Constants
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.mapper.toCategoryModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SelectCategoryViewModel @Inject constructor(private val getCategoriesUseCase: GetCategoriesUseCase) : ViewModel() {

    private val _categories: MutableLiveData<List<CategoryModel>> by lazy {
        MutableLiveData<List<CategoryModel>>().also {
            it.value = emptyList()
        }
    }
    val categories: LiveData<List<CategoryModel>> = _categories

    fun getCategories() = viewModelScope.launch {
        val timeForDelay = categories.value?.size ?: 0
        if(timeForDelay == 0){
            delay(Constants.SHIMMER_EFFECT_DELAY) //Delay for show shimmer effect
        }
        getCategoriesUseCase()
            .catch { exception ->
                Timber.e(exception)
            }
            .collect { list ->
                _categories.value = list.map { it.toCategoryModel() }
            }
    }
}