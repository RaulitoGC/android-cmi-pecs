package com.cmi.presentation.components.remover.type

import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.DeleteCategoriesUseCase
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.presentation.Constants.SHIMMER_EFFECT_DELAY
import com.cmi.presentation.R
import com.cmi.presentation.components.remover.PictureRemoverForPecsViewModel
import com.cmi.presentation.manager.StringResourceManager
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.model.mapper.toCategory
import com.cmi.presentation.model.mapper.toCategoryModel
import com.cmi.presentation.utils.MessageBuilder
import com.cmi.presentation.utils.MessageType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class CategoryRemoverForPecsViewmodel(
    messageBuilder: MessageBuilder,
    private val stringResourceManager: StringResourceManager,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val deleteCategoriesUseCase: DeleteCategoriesUseCase
) : PictureRemoverForPecsViewModel(messageBuilder, stringResourceManager) {

    init {
        getExternalCategories()
        updateDescription(stringResourceManager.getString(R.string.text_delete_categories_description))
    }

    private fun getExternalCategories() = viewModelScope.launch {
        showLoading(isLoading = true)
        val timeForDelay = uiState.value.pictureModels.size
        if(timeForDelay == 0){
            delay(SHIMMER_EFFECT_DELAY) //Delay for show shimmer effect
        }

        getCategoriesUseCase().catch { throwable ->
                Timber.e(throwable)
                showLoading(isLoading = false)
                showToastMessage(MessageType.GeneralError)
            }.collect { list ->
                showLoading(isLoading = false)
                val pictureModels = list.filter {
                    it.isExternal == true
                }.map {
                    it.toCategoryModel(
                        isSelectedForUiEnabled = true
                    )
                }
                showPictures(pictureModels)
            }
    }

    override fun removePictures(picturesSelected: List<PictureModel>) {
        viewModelScope.launch {
            if (picturesSelected.isNotEmpty()) {
                val categories = picturesSelected.mapNotNull {
                    (it as? CategoryModel)?.toCategory()
                }
                deleteCategoriesUseCase(categories = categories).catch { throwable ->
                        Timber.e(throwable)
                        showToastMessage(MessageType.GeneralError)
                    }.collect {
                        showToastMessage(MessageType.Success(R.string.text_delete_categories_success))
                    }
            }
        }
    }

    override fun getPrincipalTitle(): String {
        return stringResourceManager.getString(R.string.text_delete_category)
    }

    override fun getDescription(): String {
        return stringResourceManager.getString(R.string.text_delete_categories_description)
    }
}