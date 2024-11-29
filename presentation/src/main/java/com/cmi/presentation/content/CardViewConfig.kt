package com.cmi.presentation.content

import androidx.annotation.DimenRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.cmi.presentation.R

data class CardViewConfig(
    @DimenRes val size: Int = R.dimen.card_view_config_size,
    @DimenRes val imageSize: Int = R.dimen.card_view_config_image_size,
    @DimenRes val fontSize: Int = R.dimen.card_view_config_text_size,

    @DimenRes val cornerRadius: Int = R.dimen.card_view_corner_radius,
    @DimenRes val borderStroke: Int = R.dimen.card_view_border_stroke,
    @DimenRes val elevation: Int = R.dimen.card_view_elevation
)