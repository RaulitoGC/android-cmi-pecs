package com.cmi.presentation.components.common.title

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.ktx.ArrowIcon
import com.cmi.presentation.ktx.DefaultHorizontalSpacer
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun DefaultTitle(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    onBackClick: () -> Unit
) {
    Title(modifier = modifier, stringRestTitle = title, onBackClick = onBackClick)
}

@Composable
fun DefaultTitle(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit
) {
    Title(modifier = modifier, title = title, onBackClick = onBackClick)
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
    @StringRes stringRestTitle: Int = 0,
    onBackClick: () -> Unit,
    title: String = ""
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth().height(56.dp),
        backgroundColor = CmiThemeExtensions.colors.primarySurface,
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
            text = if(stringRestTitle == 0 ) title else stringResource(id = stringRestTitle),
            style = CmiThemeExtensions.typography.h1,
            color = CmiThemeExtensions.colors.primaryText,
        )
    }
}