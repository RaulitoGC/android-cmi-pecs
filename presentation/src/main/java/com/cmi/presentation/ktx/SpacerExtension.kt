package com.cmi.presentation.ktx

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun DefaultHorizontalSpacer(width: Dp = 16.dp) {
    Spacer(modifier = Modifier.width(width = width))
}

@Composable
fun RemainingSpacer(modifier: Modifier){
    Spacer(modifier = modifier)
}

@Composable
fun DefaultVerticalSpacer(
    modifier: Modifier = Modifier,
    height: Dp = 16.dp
) {
    Spacer(modifier = modifier.height(height = height))
}