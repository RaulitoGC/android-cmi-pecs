package com.cmi.presentation.pecs.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.CleanLastPictogramsUseCase
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.presentation.Constants
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.mapper.getCategoriesMapFormat
import com.cmi.presentation.model.mapper.toCategoryModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class CategoryViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val cleanLastPictogramsUseCase: CleanLastPictogramsUseCase,
    private val itemsPerScreen: Int
) : ViewModel() {

    private val _categories: MutableLiveData<Map<Int, List<CategoryModel>>> by lazy {
        MutableLiveData<Map<Int, List<CategoryModel>>>().also {
            it.value = emptyMap()
        }
    }
    val categories: LiveData<Map<Int, List<CategoryModel>>> = _categories

    init {
        cleanLastPecsPictograms()
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch {
        delay(Constants.SHIMMER_EFFECT_DELAY) //Delay for show shimmer effect
        getCategoriesUseCase()
            .catch { exception ->
                Timber.e(exception)
            }
            .collect { list ->
                _categories.value = getCategoriesMapFormat(
                    itemsPerScreen = itemsPerScreen,
                    items = list.filter { it.isSelected ==  true }.map {
                        it.toCategoryModel()
                    }
                )
            }
    }

    private fun cleanLastPecsPictograms() = viewModelScope.launch {
        cleanLastPictogramsUseCase().collect()
    }
}