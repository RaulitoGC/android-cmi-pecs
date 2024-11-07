package com.cmi.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CmiColors(
    val primarySurface: Color,
    val primaryColor: Color,
    val secondaryColor: Color,
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
    val primaryButtonSurface: Color,
    val windowBackground: Color,
    val dividerColor: Color,
    val colorPecsNavigation: Color
)

val LocalCmiColors = staticCompositionLocalOf {
    CmiColors(
        primarySurface = Color.Unspecified,
        primaryColor = Color.Unspecified,
        secondaryColor = Color.Unspecified,
        primaryText = Color.Unspecified,
        primaryTextDisabled = Color.Unspecified,
        primaryColorDark = Color.Unspecified,
        textButton = Color.Unspecified,
        primaryButtonSurface = Color.Unspecified,
        windowBackground = Color.Unspecified,
        dividerColor = Color.Unspecified,
        colorPecsNavigation = Color.Unspecified
    )
}
