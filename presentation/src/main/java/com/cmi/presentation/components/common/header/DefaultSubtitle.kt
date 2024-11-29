package com.cmi.presentation.components.common.header

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmi.presentation.R
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun DefaultSubTitle(
    modifier: Modifier = Modifier,
    @StringRes subTitle: Int,
    padding: Dp = 24.dp
) {
    Column {
        Text(
            modifier = modifier.fillMaxWidth().padding(horizontal = padding),
            text = stringResource(id = subTitle),
            fontSize = dimensionResource(R.dimen.subtitle_text_size).value.sp,
            style = CmiThemeExtensions.typography.body
        )
        Divider(
            color = CmiThemeExtensions.colors.dividerColor,
            modifier = Modifier.fillMaxWidth().padding(horizontal = padding)
        )
    }
}