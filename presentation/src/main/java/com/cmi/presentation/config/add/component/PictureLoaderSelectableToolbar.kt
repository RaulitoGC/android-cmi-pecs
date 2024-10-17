package com.cmi.presentation.config.add.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@SuppressLint("ResourceType")
@Composable
fun PictureLoaderSelectableToolbar(
    titleConfig: SelectableTitleConfig,
    onUpdate: () -> Unit
) {

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        backgroundColor = CmiThemeExtensions.colors.primarySurface
    ) {
        DefaultHorizontalSpacer(12.dp)
        ArrowIcon()
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
            text = stringResource(R.string.text_update),
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