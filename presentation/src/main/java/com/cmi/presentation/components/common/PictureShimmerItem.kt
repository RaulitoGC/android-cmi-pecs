package com.cmi.presentation.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.ktx.shimmerEffect

@Composable
fun PictureShimmerItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
) {

    if (isLoading) {

        val cardViewConfig = CardViewConfig(
            size = dimensionResource(id = R.dimen.picture_shimmer_size)
        )

        PictureCardView(
            modifier = modifier,
            cardViewConfig = cardViewConfig,
            onItemSelected = { _, _ ->

            }
        ) {
            Column {
                DefaultVerticalSpacer(height = 16.dp)
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(0.8f)
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.margin_normal),
                        )
                        .shimmerEffect()
                )
                DefaultVerticalSpacer(height = 8.dp)
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(0.2f)
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.margin_normal),
                        )
                        .shimmerEffect()
                )
                DefaultVerticalSpacer(height = 16.dp)
            }
        }
    } else {
        contentAfterLoading()
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_ShimmerEffectItem() {
    PictureShimmerItem(
        modifier = Modifier,
        isLoading = true,
        contentAfterLoading = {}
    )
}