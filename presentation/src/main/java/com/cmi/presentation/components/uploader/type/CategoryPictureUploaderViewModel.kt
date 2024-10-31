package com.cmi.presentation.components.uploader.type

import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.AddCategoryUseCase
import com.cmi.domain.usecase.GetCategoryByIdUseCase
import com.cmi.presentation.R
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.components.uploader.PictureUploaderViewModel
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.model.mapper.toCategory
import com.cmi.presentation.model.mapper.toCategoryModel
import com.cmi.presentation.utils.MessageBuilder
import com.cmi.presentation.utils.MessageType
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class CategoryPictureUploaderViewModel(
    contentType: PictureUploaderContentType,
    messageBuilder: MessageBuilder,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
) : PictureUploaderViewModel(contentType, CategoryModel(), messageBuilder) {

    init {
        if (contentType is PictureUploaderContentType.CategoryEditable) {
            getCategoryById(contentType.pictureId)
        }

        if(contentType is PictureUploaderContentType.CategoryEntry) {
            updatePictureModel(uiState.value.pictureModel.copyIsExternal(isExternal = true))
        }
    }

    private fun getCategoryById(categoryId: Int) = viewModelScope.launch {
        getCategoryByIdUseCase.invoke(categoryId)
            .catch { exception ->
                Timber.e(exception)
                showMessage(MessageType.GeneralError)
            }
            .collect { category ->
                updatePictureModel(category.toCategoryModel())
            }
    }

    override fun uploadPicture(pictureModel: PictureModel?) {
        viewModelScope.launch {
            if (isValidForm(pictureModel?.name, pictureModel?.path)) {
                val categoryModel = CategoryModel(
                    folder = pictureModel?.name?.replace("\\s".toRegex(), ""),
                    path = pictureModel?.name,
                    name = pictureModel?.path,
                    priority = 0,
                    isExternal = true,
                    isSelectedForPecs = true
                )
                addCategoryUseCase(category = categoryModel.toCategory())
                    .catch {
                        showMessage(MessageType.GeneralError)
                    }.collect {
                        showMessage(MessageType.Success(R.string.text_category_add_success))
                        cleanFields()
                    }
            }
        }
    }

    private fun isValidForm(pictureName: String?, pictureFileName: String?): Boolean {
        if (pictureName.isNullOrEmpty()) {
            showMessage(MessageType.Error(R.string.text_configuration_error_empty_name))
            return false
        }

        if (pictureFileName.isNullOrEmpty()) {
            showMessage(MessageType.Error(R.string.text_configuration_error_empty_image))
            return false
        }

        return true
    }
}