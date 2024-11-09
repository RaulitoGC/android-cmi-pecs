package com.cmi.presentation.pecs.pictogram

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cmi.presentation.R
import com.cmi.presentation.components.common.PictureCardView
import com.cmi.presentation.components.common.image.DefaultImage
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.ktx.getUriFromPath
import com.cmi.presentation.ktx.isTrue
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun PictogramCloseableItemContent(
    modifier: Modifier = Modifier,
    pictogramModel: PictogramModel,
    cardViewConfig: CardViewConfig,
    onItemSelectedForRemoval: (pictogramModel: PictogramModel) -> Unit
) {

    Box {

        PictureCardView(
            modifier = modifier.padding(top = 4.dp, end = 4.dp),
            cardViewConfig = cardViewConfig,
            pictureModel = pictogramModel,
            onItemSelected = {

            }
        ) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultVerticalSpacer(height = 4.dp)

                val imageSize = dimensionResource(cardViewConfig.imageSize)
                val fontSize = dimensionResource(cardViewConfig.fontSize)

                Image(
                    modifier = modifier.size(
                        if (pictogramModel.isSelectedForUiEnabled.isTrue) imageSize * 0.9f
                        else imageSize
                    ),
                    painter = rememberAsyncImagePainter(getUriFromPath(pictogramModel)),
                    contentDescription = pictogramModel.name.orEmpty(),
                )
                DefaultVerticalSpacer(height = 2.dp)
                Text(
                    modifier = modifier,
                    text = pictogramModel.name.orEmpty(),
                    fontSize = if (pictogramModel.isSelectedForUiEnabled.isTrue) fontSize.value.sp else ((fontSize.value + 4).sp)
                )
                DefaultVerticalSpacer(height = 4.dp)
            }

        }

        DefaultImage(
            modifier = Modifier
                .size(dimensionResource(R.dimen.pictogram_stripe_phrase_close))
                .background(
                    color = CmiThemeExtensions.colors.primaryColor,
                    shape = RoundedCornerShape(32.dp)
                )
                .align(alignment = Alignment.TopEnd)
                .clickable {
                    onItemSelectedForRemoval(pictogramModel)
                },
            drawableRes = R.drawable.ic_close
        )
    }
}
