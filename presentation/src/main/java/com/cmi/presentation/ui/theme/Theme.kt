package com.cmi.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.cmi.presentation.R


@Composable
fun CmiAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val cmiColors = CmiColors(
        primarySurface = colorResource(R.color.colorPrimaryDark),
        primaryText = colorResource(R.color.colorPrimaryText),
        primaryTextDisabled = colorResource(R.color.colorPrimaryTextDisable),
        primaryColorDark = colorResource(R.color.colorPrimaryDark)
    )

    val cmiTypography = CmiTypography()

    CompositionLocalProvider(
        LocalCmiColors provides cmiColors,
        LocalCmiTypography provides cmiTypography
    ) {
        MaterialTheme(
            content = content
        )
    }
}


object CmiThemeExtensions {

    val colors: CmiColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCmiColors.current

    val typography: CmiTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalCmiTypography.current
}