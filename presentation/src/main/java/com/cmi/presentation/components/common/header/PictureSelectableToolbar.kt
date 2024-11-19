package com.cmi.presentation.components.common.header

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.config.add.model.SelectableTitleConfig
import com.cmi.presentation.ktx.ArrowIcon
import com.cmi.presentation.ktx.DefaultHorizontalSpacer
import com.cmi.presentation.ktx.RemainingSpacer
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun PictureSelectableToolbar(
    titleConfig: SelectableTitleConfig,
    @StringRes actionTitle: Int = R.string.text_update,
    onBackClick: () -> Unit,
    onUpdate: () -> Unit
) {

    TopAppBar(
        modifier = Modifier
            .background(CmiThemeExtensions.colors.primarySurface)
            .padding(top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
            .fillMaxWidth()
            .height(56.dp),
        backgroundColor = CmiThemeExtensions.colors.primarySurface
    ) {
        DefaultHorizontalSpacer(12.dp)
        ArrowIcon(onBackClick)
        DefaultHorizontalSpacer(12.dp)
        Image(
            modifier = Modifier.padding(vertical = 8.dp),
            painter = painterResource(R.drawable.ic_cmi_boy),
            contentDescription = null
        )
        DefaultHorizontalSpacer()
        Text(
            style = CmiThemeExtensions.typography.h1,
            color = CmiThemeExtensions.colors.primaryText,
            text = titleConfig.title
        )

        RemainingSpacer(Modifier.weight(1f))
        Text(
            text = stringResource(actionTitle),
            style = CmiThemeExtensions.typography.h1,
            color = CmiThemeExtensions.colors.primaryText,
            modifier = Modifier
                .alpha(if (titleConfig.isActionEnabled) 1f else 0.4f)
                .clickable(enabled = titleConfig.isActionEnabled) {
                    onUpdate()
                }
        )
        DefaultHorizontalSpacer(12.dp)
    }
}