package com.cmi.presentation.components.common

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import coil.util.DebugLogger
import com.cmi.presentation.R
import com.cmi.presentation.config.add.model.PictureLoaderEvent
import com.cmi.presentation.ktx.shimmerEffect
import timber.log.Timber

@Composable
fun PicturePreview(
    imageUri: Uri?
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

        PreviewImage(
            imageUri = imageUri
        )

    }
}

@Composable
fun PreviewImage(
    imageUri: Uri?
) {
    Timber.d("Preview ImageUri $imageUri")
    val borderColor = colorResource(id = R.color.colorPictogramBorder)
    val imageModifier = Modifier
        .border(BorderStroke(2.dp, borderColor))
        .background(Color.Transparent)
        .padding(dimensionResource(id = R.dimen.margin_normal))

    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    var showErrorMessage by remember {
        mutableStateOf(false)
    }

    if (imageUri != null) {
        if(showErrorMessage) {
            ShowErrorToastMessage()
        } else {
            AsyncImage(
                imageLoader = imageLoader,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Uri.parse(imageUri.toString()))
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_image_preview),
                error = painterResource(R.drawable.ic_cmi_boy),
                contentDescription = stringResource(id = R.string.text_content_description_loading),
                contentScale = ContentScale.Crop,
                modifier = imageModifier,
                onError = {
                    showErrorMessage = true
                },
                onSuccess = {
                    showErrorMessage = false
                }
            )
        }
    } else {
        Image(
            painter = painterResource(R.drawable.ic_image_preview),
            contentDescription = stringResource(id = R.string.text_content_description_loading),
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
    }
}