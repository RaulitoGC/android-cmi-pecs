package com.cmi.presentation.config.add.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmi.presentation.ktx.ArrowIcon
import com.cmi.presentation.ktx.DefaultHorizontalSpacer

@Composable
fun PictureLoaderTitle(@StringRes title: Int) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        contentPadding = PaddingValues(start = 12.dp)
    ) {
        ArrowIcon()
        DefaultHorizontalSpacer()
        Text(
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp,
            text = stringResource(id = title)
        )
    }
}