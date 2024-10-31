package com.cmi.presentation.components.selecter.type

import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.domain.usecase.UpdateCategoriesUseCase
import com.cmi.presentation.Constants.SHIMMER_EFFECT_DELAY
import com.cmi.presentation.R
import com.cmi.presentation.components.selecter.PictureSelecterForPecsViewModel
import com.cmi.presentation.manager.StringResourceManager
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.model.mapper.toCategory
import com.cmi.presentation.model.mapper.toCategoryModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class CategorySelecterForPecsViewModel(
    private val stringResourceManager: StringResourceManager,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
): PictureSelecterForPecsViewModel(stringResourceManager) {

    init {
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch {
        showLoading(isLoading = true)
        val timeForDelay = uiState.value.pictureModels.size
        if (timeForDelay == 0) {
            delay(SHIMMER_EFFECT_DELAY)
        }

        getCategoriesUseCase()
            .catch {
                showLoading(isLoading = false)
                showErrorMessage()
            }
            .collect { list ->
                showLoading(isLoading = false)
                showPictures(
                    pictureModels = list.map {
                        it.toCategoryModel(isSelectedForUiEnabled = true)
                    }
                )
        }
    }

    override fun getSuccessMessage(): String {
        return stringResourceManager.getString(R.string.text_select_categories_success)
    }

    override fun onUpdatePicturesSelected(pictureModels: List<PictureModel>) {
        viewModelScope.launch {
            if (pictureModels.isNotEmpty()) {
                val categories = pictureModels.mapNotNull {
                    (it as? CategoryModel)?.toCategory()
                }
                updateCategoriesUseCase(categories = categories)
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