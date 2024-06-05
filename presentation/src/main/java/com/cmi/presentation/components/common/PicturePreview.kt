package com.cmi.presentation.components.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R

@Composable
fun PicturePreview(
    isLoading: Boolean
) {
    Card(
        modifier = Modifier
            .size(
                width = dimensionResource(id = R.dimen.picture_preview_size_width),
                height = dimensionResource(id = R.dimen.picture_preview_size_height)
            ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, colorResource(id = R.color.colorPictogramBorder)),
        elevation = 8.dp
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            PreviewImage()
        }
    }
}

@Composable
fun PreviewImage() {
    val borderColor = colorResource(id = R.color.colorPictogramBorder)
    val imageModifier = Modifier
        .border(BorderStroke(2.dp, borderColor))
        .background(Color.Transparent)

    Image(
        painter = painterResource(id = R.drawable.ic_image_preview),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(id = R.string.text_content_description_loading),
        modifier = imageModifier
    )
}