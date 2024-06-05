package com.cmi.presentation.config.add.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.cmi.presentation.R
import com.cmi.presentation.config.add.model.PictureLoaderState

@Composable
fun PictureLoaderSubmit(
    modifier: Modifier,
    state: PictureLoaderState
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.margin_high),
                vertical = dimensionResource(id = R.dimen.margin_medium)
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        PictureLoaderUploadButton(text = state.contentType.uploadText) {

        }
    }
}

@Composable
fun PictureLoaderUploadButton(
    @StringRes text: Int,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text(text = stringResource(id = text))
    }
}