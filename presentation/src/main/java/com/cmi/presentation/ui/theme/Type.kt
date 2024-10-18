package com.cmi.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cmi.presentation.R

data class CmiTypography(
    val h1: TextStyle = DefaultCmiTypography.h1,
    val body: TextStyle = DefaultCmiTypography.body1,
    val button: TextStyle = DefaultCmiTypography.button
)

val LocalCmiTypography = staticCompositionLocalOf {
    CmiTypography()
}

val DefaultCmiTypography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.poppins_bold))
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.poppins_regular))
    ),
    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.poppins_bold))
    )
)