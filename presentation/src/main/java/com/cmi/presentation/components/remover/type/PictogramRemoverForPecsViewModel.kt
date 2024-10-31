package com.cmi.presentation.components.remover.type

import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.DeletePictogramsUseCase
import com.cmi.domain.usecase.GetPictogramsByCategoryUseCase
import com.cmi.presentation.R
import com.cmi.presentation.components.remover.PictureRemoverForPecsViewModel
import com.cmi.presentation.manager.StringResourceManager
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.model.mapper.toPictogram
import com.cmi.presentation.model.mapper.toPictogramModel
import com.cmi.presentation.utils.MessageBuilder
import com.cmi.presentation.utils.MessageType
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class PictogramRemoverForPecsViewModel(
    categoryId: Int,
    messageBuilder: MessageBuilder,
    private val stringResourceManager: StringResourceManager,
    private val getPictogramsByCategoryUseCase: GetPictogramsByCategoryUseCase,
    private val deletePictogramsUseCase: DeletePictogramsUseCase
) : PictureRemoverForPecsViewModel(messageBuilder, stringResourceManager) {

    init {
        getExternalPictogramsByCategory(categoryId)
        updateDescription(stringResourceManager.getString(R.string.text_delete_pictograms_description))
    }

    private fun getExternalPictogramsByCategory(categoryId: Int) = viewModelScope.launch {
        getPictogramsByCategoryUseCase(categoryId = categoryId)
            .catch { throwable ->
                Timber.e(throwable)
                showToastMessage(MessageType.GeneralError)
            }
            .collect { pictograms ->
                val pictureModels = pictograms.filter {
                    it.isExternal == true
                }.map {
                    it.toPictogramModel(
                        isSelectedForUiEnabled = true
                    )
                }
                showPictures(pictureModels)
            }
    }

    override fun removePictures(picturesSelected: List<PictureModel>) {
        viewModelScope.launch {
            if (picturesSelected.isNotEmpty()) {
                val pictograms = picturesSelected.mapNotNull {
                    (it as? PictogramModel)?.toPictogram()
                }
                deletePictogramsUseCase(pictograms = pictograms)
                    .catch { throwable ->
                        Timber.e(throwable)
                        showToastMessage(MessageType.GeneralError)
                    }.collect {
                        showToastMessage(MessageType.Success(R.string.text_delete_pictograms_success))
                    }
            }
        }
    }

    override fun getPrincipalTitle(): String {
        return stringResourceManager.getString(R.string.text_delete_pictogram)
    }

    override fun getDescription(): String {
        return stringResourceManager.getString(R.string.text_delete_pictograms_description)
    }
}