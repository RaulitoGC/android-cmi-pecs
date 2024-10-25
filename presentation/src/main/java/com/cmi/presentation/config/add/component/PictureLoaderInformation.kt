package com.cmi.presentation.config.add.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cmi.presentation.config.add.model.PictureUploaderEvent
import com.cmi.presentation.config.add.model.PictureUploaderState

@Composable
fun PictureLoaderInformation(
    modifier: Modifier,
    state: PictureUploaderState,
    handleEvent: (PictureUploaderEvent) -> Unit
) {
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(horizontal = dimensionResource(id = R.dimen.margin_high))
//    ) {
//        Column(
//            modifier = modifier
//                .fillMaxWidth()
//                .weight(1f)
//        ) {
//            PictureNameTextField(
//                pictureName = state.pictureName,
//                onPictureNameChange = {
//                    handleEvent(PictureLoaderEvent.NameChanged(it))
//                }
//            )
//            DefaultVerticalSpacer()
//            PictureImageSources(
//                onPictureTaken = {
//                    handleEvent(PictureLoaderEvent.ImageUriUpdated(it))
//                }
//            )
//        }
//        Column(
//            modifier = modifier
//                .weight(1f),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            PicturePreview(
//                imageUri = state.imageUri
//            )
//        }
//    }
}

@Preview(showBackground = true)
@Composable
fun Preview_PictureLoaderInformation() {
//    PictureLoaderInformation(
//        modifier = Modifier.fillMaxWidth(),
//        state = PictureLoaderState(),
//        handleEvent = {}
//    )
}