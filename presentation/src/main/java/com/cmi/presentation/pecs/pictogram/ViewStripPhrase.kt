package com.cmi.presentation.pecs.pictogram

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.components.common.image.DefaultImage
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun ViewStripPhrase(
    modifier: Modifier = Modifier,
    cardViewConfig: CardViewConfig,
    handleEvent: (event: PecsFlowPictogramSelectionEvent) -> Unit,
    picturesForPecs: List<PictogramModel>
) {
    Row(
        modifier = modifier
    ) {
        ViewStripPhraseContent(
            modifier = Modifier
                .weight(0.85f)
                .align(alignment = Alignment.CenterVertically),
            handleEvent = handleEvent,
            picturesForPecs = picturesForPecs,
            cardViewConfig = cardViewConfig
        )
        DefaultImage(
            modifier = Modifier
                .weight(0.15f)
                .wrapContentSize()
                .align(alignment = Alignment.CenterVertically)
                .clipToBounds()
                .clip(RoundedCornerShape(72.dp))
                .clickable {
                    handleEvent(PecsFlowPictogramSelectionEvent.ExecuteSound)
                },
            drawableRes = R.drawable.ic_volume_up,
        )
    }
}

@Composable
fun ViewStripPhraseContent(
    modifier: Modifier = Modifier,
    picturesForPecs: List<PictogramModel>,
    cardViewConfig: CardViewConfig,
    handleEvent: (event: PecsFlowPictogramSelectionEvent) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.pecs_stripe_size_height))
                .padding(start = 16.dp, end = 16.dp)
                .background(
                    color = CmiThemeExtensions.colors.primaryColor,
                    shape = RoundedCornerShape(
                        dimensionResource(R.dimen.stripe_view_corner_radius)
                    )
                )
        )

        Row {
            picturesForPecs.forEach { pictogramModel ->
                PictogramCloseableItemContent(
                    pictogramModel = pictogramModel,
                    cardViewConfig = cardViewConfig,
                    onItemSelectedForRemoval = {
                        handleEvent(PecsFlowPictogramSelectionEvent.OnPictogramRemoved(pictogramModel))
                    }
                )
            }
        }
    }
}