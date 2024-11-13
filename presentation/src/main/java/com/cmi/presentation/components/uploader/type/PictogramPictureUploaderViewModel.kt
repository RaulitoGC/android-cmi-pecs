package com.cmi.presentation.components.uploader.type


import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.AddPictogramUseCase
import com.cmi.domain.usecase.GetCategoriesUseCase
import com.cmi.domain.usecase.GetPictogramByIdUseCase
import com.cmi.presentation.R
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.components.uploader.PictureUploaderEvent
import com.cmi.presentation.components.uploader.PictureUploaderViewModel
import com.cmi.presentation.ktx.orNegative
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.model.mapper.getCategoriesSelectableMapFormat
import com.cmi.presentation.model.mapper.toCategoryModel
import com.cmi.presentation.model.mapper.toCategorySelectableModelUnChecked
import com.cmi.presentation.model.mapper.toPictogram
import com.cmi.presentation.model.mapper.toPictogramModel
import com.cmi.presentation.utils.MessageBuilder
import com.cmi.presentation.utils.MessageType
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class PictogramPictureUploaderViewModel(
    private val contentType: PictureUploaderContentType,
    messageBuilder: MessageBuilder,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getPictogramByIdUseCase: GetPictogramByIdUseCase,
    private val addPictogramUseCase: AddPictogramUseCase,
): PictureUploaderViewModel(contentType, PictogramModel(isExternal = true), messageBuilder) {

    init {
        getCategories()

        if(contentType is PictureUploaderContentType.PictogramEditable) {
            getPictogramById(contentType.pictureId)
        }
    }

    override fun handleEvent(event: PictureUploaderEvent) {
        when (event) {
            is PictureUploaderEvent.CategorySelected -> {
                onCategorySelected(event)
            }
            else -> {
                super.handleEvent(event)
            }
        }
    }

    private fun onCategorySelected(event: PictureUploaderEvent.CategorySelected) {
        val categoryModelSelected = event.categoryModel
        val categories = uiState.value.categories
        (uiState.value.pictureModel as? PictogramModel)?.let { pictogramModel ->
            updatePictureModel(
                pictogramModel.copy(
                    categoryId = categoryModelSelected.id,
                    categoryName = categoryModelSelected.name
                )
            )
            updateCategories(
                categories = categories.map { category ->
                    if(category.id == categoryModelSelected.id){
                        category.copy(isSelected = true)
                    } else {
                        category.copy(isSelected = false)
                    }
                }
            )
        }
    }

    private fun getPictogramById(pictogramId: Int) = viewModelScope.launch {
        getPictogramByIdUseCase.invoke(pictogramId)
            .catch { exception ->
                Timber.e(exception)
                showMessage(MessageType.GeneralError)
            }
            .collect { pictogram ->
                updatePictureModel(pictureModel = pictogram.toPictogramModel())
            }
    }

    private fun getCategories() = viewModelScope.launch {
        getCategoriesUseCase()
            .catch { exception ->
                Timber.e(exception)
                showMessage(MessageType.GeneralError)
            }
            .collect { list ->
                updateCategories(
                    list.map {
                        it.toCategoryModel().copy(isSelectedForUiEnabled = true, isSelected = false)
                    }
                )
            }
    }

    override fun uploadPicture(pictureModel: PictureModel?) {
        viewModelScope.launch {
            (pictureModel as? PictogramModel)?.let {
                val name = it.name
                val path = it.path
                val categoryId = it.categoryId
                val categoryName = it.categoryName
                if(isValidForm(it.name, it.path, it.categoryId, it.categoryName)){
                    val pictogramModel = PictogramModel(
                        folder = categoryName,
                        path = path,
                        name = name,
                        priority = 0,
                        isExternal = true,
                        categoryId = categoryId,
                        isSelectedForPecs = true
                    )

                    addPictogramUseCase(pictogram = pictogramModel.toPictogram())
                        .catch {
                            showMessage(MessageType.GeneralError)
                        }.collect {
                            showMessage(getSuccessMessageType())
                            cleanFields()
                            navigateBack()
                        }
                }
            }
        }
    }

    private fun getSuccessMessageType(): MessageType.Success {
        val successResString = if(contentType is PictureUploaderContentType.CategoryEntry) {
            R.string.text_pictogram_added_success
        } else {
            R.string.text_edit_pictogram_success
        }
        return MessageType.Success(successResString)
    }

    private fun isValidForm(pictogramName: String?, pictureFileName: String?, categoryId: Int?, categoryName: String?): Boolean{

        if(categoryId.orNegative < 0 || categoryName.isNullOrEmpty()){
            showMessage(MessageType.Error(R.string.text_configuration_error_empty_category))
            return false
        }

        if(pictogramName.isNullOrEmpty()){
            showMessage(MessageType.Error(R.string.text_configuration_error_empty_name))
            return false
        }

        if(pictureFileName.isNullOrEmpty()){
            showMessage(MessageType.Error(R.string.text_configuration_error_empty_image))
            return false
        }

        return true
    }

}