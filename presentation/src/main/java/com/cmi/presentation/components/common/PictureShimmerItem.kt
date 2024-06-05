package com.cmi.presentation.components.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.ktx.shimmerEffect

@Composable
fun PictureShimmerItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
) {
    if(isLoading) {
        val width = dimensionResource(id = R.dimen.picture_preview_size_width)
        val height = dimensionResource(id = R.dimen.picture_preview_size_height)

        Card(
            modifier = modifier
                .size(
                    width = width,
                    height = height
                ),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, colorResource(id = R.color.colorPictogramBorder)),
            elevation = 8.dp
        ) {
            Column {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(height - 60.dp)
                        .padding(16.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = modifier.width(16.dp))
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(30.dp)
                        .shimmerEffect()
                )
            }
        }
    } else {
        contentAfterLoading()
    }
}