package com.cmi.presentation.components.common.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun DefaultImage(
    modifier: Modifier = Modifier,
    @DrawableRes drawableRes: Int,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    Image(
        modifier = modifier,
        painter = painterResource(drawableRes),
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}