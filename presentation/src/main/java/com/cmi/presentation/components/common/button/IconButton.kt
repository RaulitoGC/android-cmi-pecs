package com.cmi.presentation.components.common.button

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun RowScope.PreviousButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier
            .align(alignment = Alignment.CenterVertically)
            .padding(horizontal = 16.dp),
        backgroundColor = CmiThemeExtensions.colors.colorPecsNavigation,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_navigation_arrow_back),
            tint = Color.White,
            contentDescription = "Previous"
        )
    }
}

@Composable
fun RowScope.NextButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier
            .align(alignment = Alignment.CenterVertically)
            .padding(horizontal = 16.dp),
        backgroundColor = CmiThemeExtensions.colors.colorPecsNavigation,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_navigation_arrow_front),
            tint = Color.White,
            contentDescription = "Previous"
        )
    }
}