package com.cmi.presentation.content

import androidx.annotation.DimenRes
import androidx.compose.ui.unit.Dp
import com.cmi.presentation.R

data class CardViewConfig(
    val size: Dp,
    @DimenRes val cornerRadius: Int = R.dimen.card_view_corner_radius,
    @DimenRes val borderStroke: Int = R.dimen.card_view_border_stroke,
    @DimenRes val elevation: Int = R.dimen.card_view_elevation
)