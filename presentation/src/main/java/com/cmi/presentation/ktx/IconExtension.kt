package com.cmi.presentation.ktx

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cmi.presentation.R

@Composable
fun ArrowIcon() = Icon(
    tint = MaterialTheme.colors.onSurface,
    imageVector = Icons.Default.ArrowBack,
    contentDescription = stringResource(id = R.string.text_content_description_icon_back)
)