package com.cmi.presentation.config.add

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.cmi.presentation.components.common.PictureImageSources
import com.cmi.presentation.components.common.PictureNameTextField
import com.cmi.presentation.components.common.PicturePreview
import com.cmi.presentation.components.common.add.PictureLoaderContentType
import com.cmi.presentation.components.common.add.PictureLoaderUploadButton
import com.cmi.presentation.components.common.title.DefaultTitle
import com.cmi.presentation.config.add.component.CarouselWithButtons
import com.cmi.presentation.config.add.model.PictureLoaderEvent
import com.cmi.presentation.config.add.model.PictureLoaderState
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.ktx.centerHorizontallyFromParentTo
import com.cmi.presentation.ktx.centerHorizontallyToParent
import com.cmi.presentation.ktx.centerHorizontallyToParentFrom
import com.cmi.presentation.ktx.fullLinkToBottom
import com.cmi.presentation.ui.theme.CmiAppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PictureLoader(contentType: PictureLoaderContentType) {

    val viewModel: PictureLoaderViewModel = koinViewModel {
        parametersOf(contentType)
    }

    CmiAppTheme {
        val state = viewModel.uiState.collectAsState().value
        PictureLoaderContent(
            modifier = Modifier.fillMaxSize(),
            state = state,
            handleEvent = viewModel::handleEvent
        )
    }
}

//TODO: Move Constraints to PictureLoaderConstraints.kt
@Composable
private fun PictureLoaderContent(
    modifier: Modifier = Modifier,
    state: PictureLoaderState,
    handleEvent: (event: PictureLoaderEvent) -> Unit,
) {

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {

        val (title, pictureName, pictureImageSources, picturePreview, submitButton) = createRefs()
        val (titleSpacer, pictureNameSpacer) = createRefs()
        val middleGuideline = createGuidelineFromStart(0.5f)

        DefaultTitle(
            modifier = applyTitleConstraints(title),
            title = state.contentType.title
        )

        DefaultVerticalSpacer(
            modifier = applyTitleSpacerConstraints(titleSpacer, title),
            height = 32.dp
        )

        PictureNameTextField(
            modifier = applyPictureNameConstraints(pictureName, middleGuideline, titleSpacer),
            pictureName = state.pictureName,
            onPictureNameChange = {
                handleEvent(PictureLoaderEvent.NameChanged(it))
            }
        )

        DefaultVerticalSpacer(
            modifier = applyPictureNameSpacerConstraints(pictureNameSpacer, middleGuideline, pictureName),
            height = 32.dp
        )

        PictureImageSources(
            modifier = applyPictureImageResourcesConstraints(pictureImageSources, middleGuideline, pictureNameSpacer),
            onPictureTaken = {
                handleEvent(PictureLoaderEvent.ImageUriUpdated(it))
            }
        )

        PicturePreview(
            modifier = Modifier.constrainAs(picturePreview){
                this@constrainAs.centerHorizontallyToParentFrom(middleGuideline)
                top.linkTo(titleSpacer.bottom)
            },
            imageUri = state.imageUri
        )

        if (state.contentType.isSingleImage()) {
            CarouselWithButtons(items = state.categories)
        }

        PictureLoaderUploadButton(
            modifier = applySubmitButtonConstraints(submitButton).padding(bottom = 16.dp),
            text = state.contentType.uploadText
        ) {

        }
    }
}

@Preview(
    showBackground = true,
    heightDp = 360,
    widthDp = 800
)
@Composable
fun PictureLoaderScreenPreview() {
    PictureLoader(PictureLoaderContentType.SingleImage)
}