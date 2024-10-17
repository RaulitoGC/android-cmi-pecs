package com.cmi.presentation.config.add.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.widget.ConstraintLayout
import com.cmi.presentation.R
import com.cmi.presentation.components.common.PictureImageSources
import com.cmi.presentation.components.common.PictureNameTextField
import com.cmi.presentation.components.common.PicturePreview
import com.cmi.presentation.components.permission.PermissionExplainer
import com.cmi.presentation.config.add.model.PictureLoaderEvent
import com.cmi.presentation.config.add.model.PictureLoaderState
import com.cmi.presentation.ktx.DefaultVerticalSpacer

@Composable
fun PictureLoaderInformation(
    modifier: Modifier,
    state: PictureLoaderState,
    handleEvent: (PictureLoaderEvent) -> Unit
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