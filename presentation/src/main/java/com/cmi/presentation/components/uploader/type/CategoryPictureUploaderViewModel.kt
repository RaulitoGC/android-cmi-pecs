package com.cmi.presentation.components.uploader.type

import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.AddCategoryUseCase
import com.cmi.domain.usecase.GetCategoryByIdUseCase
import com.cmi.domain.usecase.UpdateCategoryUseCase
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
    private val contentType: PictureUploaderContentType,
    messageBuilder: MessageBuilder,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase
) : PictureUploaderViewModel(contentType, CategoryModel(isExternal = true), messageBuilder) {

    init {
        if (contentType is PictureUploaderContentType.CategoryEditable) {
            getCategoryById(contentType.pictureId)
        }
    }

    private val isEditable = contentType is PictureUploaderContentType.CategoryEditable

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
        val name = pictureModel?.name
        val path = pictureModel?.path
        if (isValidForm(name, path)) {
            val isFoundationPath = pictureModel?.isFoundationPath ?: false
            val folderName = if(isFoundationPath) pictureModel?.folder else name?.replace("\\s".toRegex(), "")
            val categoryModel = CategoryModel(
                id = pictureModel?.id,
                folder = folderName,
                path = path,
                name = name,
                priority = pictureModel?.priority ?: 0,
                isExternal = pictureModel?.isExternal ?: true,
                isSelectedForPecs = pictureModel?.isSelectedForPecs ?: true,
                isFoundationPath = isFoundationPath
            )
            insertOrUpdate(categoryModel)
        }
    }

    private fun insertOrUpdate(categoryModel: CategoryModel) = viewModelScope.launch {
        val category = categoryModel.toCategory()

        if (isEditable) {
            updateCategoryUseCase.invoke(category = category)
                .catch {
                    showMessage(MessageType.GeneralError)
                }.collect{
                    showMessage(getSuccessMessageType())
                    navigateBack()
                    cleanFields()
                }
        } else {
            addCategoryUseCase.invoke(category = category)
                .catch {
                    showMessage(MessageType.GeneralError)
                }.collect{
                    showMessage(getSuccessMessageType())
                    navigateBack()
                    cleanFields()
                }
        }
    }


    private fun getSuccessMessageType(): MessageType.Success {
        val successResString = if (contentType is PictureUploaderContentType.CategoryEntry) {
            R.string.text_category_add_success
        } else {
            R.string.text_edit_category_success
        }
        return MessageType.Success(successResString)
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