package com.cmi.presentation.components.uploader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.cmi.presentation.components.common.PictureImageSources
import com.cmi.presentation.components.common.PictureNameTextField
import com.cmi.presentation.components.common.PicturePreview
import com.cmi.presentation.components.common.add.PictureLoaderUploadButton
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.components.common.applyTitleConstraints
import com.cmi.presentation.components.common.applyTitleSpacerConstraints
import com.cmi.presentation.components.common.header.DefaultSubTitle
import com.cmi.presentation.components.common.header.DefaultTitle
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.ktx.ShowToast
import com.cmi.presentation.ktx.getImagePath
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun PictureUploader(
    contentType: PictureUploaderContentType,
    viewModel: PictureUploaderViewModel = PictureUploaderViewModel.create(contentType),
    onBack: () -> Unit
) {

    val state = viewModel.uiState.collectAsState().value
    PictureLoaderContent(
        modifier = Modifier.fillMaxSize(),
        onBack = onBack,
        state = state,
        handleEvent = viewModel::handleEvent
    )
}

@Composable
private fun PictureLoaderContent(
    modifier: Modifier = Modifier,
    state: PictureUploaderState,
    onBack: () -> Unit,
    handleEvent: (event: PictureUploaderEvent) -> Unit,
) {
    state.showMessage?.let {
        ShowToast(it)
        handleEvent(PictureUploaderEvent.MessageShown)
        onBack.invoke()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        val showCategories = state.contentType.showCategoriesCarousel()

        DefaultTitle(
            title = state.contentType.title,
            onBackClick = onBack
        )

        DefaultVerticalSpacer(
            height = if(showCategories) 8.dp else 32.dp
        )

        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .background(CmiThemeExtensions.colors.windowBackground)
                .verticalScroll(rememberScrollState()),
        ) {

            val (title, pictureName, pictureImageSources, picturePreview, submitButton) = createRefs()
            val (titleSpacer, pictureNameSpacer) = createRefs()
            val middleGuideline = createGuidelineFromStart(0.5f)

            DefaultSubTitle(
                modifier = applyTitleConstraints(title),
                padding = 64.dp,
                subTitle = state.contentType.subTitle
            )

            DefaultVerticalSpacer(
                modifier = applyTitleSpacerConstraints(titleSpacer, title),
                height = 32.dp
            )

            PictureNameTextField(
                modifier = applyPictureNameConstraints(pictureName, middleGuideline, titleSpacer),
                pictureName = state.pictureModel.name.orEmpty(),
                onPictureNameChange = {
                    handleEvent(PictureUploaderEvent.NameChanged(it))
                }
            )

            DefaultVerticalSpacer(
                modifier = applyPictureNameSpacerConstraints(
                    pictureNameSpacer,
                    middleGuideline,
                    pictureName
                ),
                height = 32.dp
            )

            PictureImageSources(
                modifier = applyPictureImageResourcesConstraints(
                    pictureImageSources,
                    middleGuideline,
                    pictureNameSpacer
                ),
                onPictureTaken = {
                    handleEvent(PictureUploaderEvent.ImageUriUpdated(it))
                }
            )

            PicturePreview(
                modifier = applyPicturePreviewConstraints(picturePreview, middleGuideline, titleSpacer),
                imagePath = state.pictureModel.getImagePath()
            )

            val carouselRef = createRef()
            if (showCategories) {

                val bottomBarrier = createBottomBarrier(pictureImageSources, picturePreview)
                CategoryCarouselSelectable(
                    modifier = applyCategoryCarouselConstraints(
                        carouselRef,
                        bottomBarrier
                    ),
                    items = state.categories
                ) {
                    handleEvent(PictureUploaderEvent.CategorySelected(it))
                }
            }

            val topItemsBarrier = createBottomBarrier(pictureImageSources, picturePreview, carouselRef)

            PictureLoaderUploadButton(
                modifier = applySubmitButtonConstraints(submitButton, topItemsBarrier).padding(bottom = 16.dp),
                text = state.contentType.submitButtonText
            ) {
                handleEvent(PictureUploaderEvent.UploadPicture)
            }
        }
    }

}
