package com.cmi.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

data class CmiColors(
    val primarySurface: Color,
    val primaryText: Color,
    val primaryTextDisabled: Color,
    val primaryColorDark: Color,
    /**
     * colorTextButton = textButton
     */
    val textButton: Color,
    /**
     * colorPrimaryButton = primaryButtonSurface
     */
    val primaryButtonSurface: Color
)

val LocalCmiColors = staticCompositionLocalOf {
    CmiColors(
        primarySurface = Color.Unspecified,
        primaryText = Color.Unspecified,
        primaryTextDisabled = Color.Unspecified,
        primaryColorDark = Color.Unspecified,
        textButton = Color.Unspecified,
        primaryButtonSurface = Color.Unspecified
    )
}
