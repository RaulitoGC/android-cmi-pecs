package com.cmi.presentation.components.common.image

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun DefaultIcon(
    modifier: Modifier = Modifier,
    @DrawableRes drawableRes: Int,
    contentDescription: String? = null,
    tintColor: Color = Color.White
) {

    Icon(
        modifier = modifier,
        painter = painterResource(drawableRes),
        contentDescription = contentDescription,
        tint = tintColor
    )
}