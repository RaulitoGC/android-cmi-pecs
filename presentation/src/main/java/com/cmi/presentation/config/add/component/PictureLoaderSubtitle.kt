package com.cmi.presentation.config.add.component

import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.cmi.presentation.R
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun PictureLoaderSubTitle(
    @StringRes subTitle: Int,
    @DimenRes horizontalSpacer: Int = R.dimen.margin_normal
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = horizontalSpacer)
            ),
        text = stringResource(id = subTitle),
        style = CmiThemeExtensions.typography.body
    )
}