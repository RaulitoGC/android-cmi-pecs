package com.cmi.presentation.components.common.header

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun DefaultDescription(
    modifier: Modifier = Modifier,
    @StringRes description: Int
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        text = stringResource(id = description),
        style = CmiThemeExtensions.typography.body
    )
}
