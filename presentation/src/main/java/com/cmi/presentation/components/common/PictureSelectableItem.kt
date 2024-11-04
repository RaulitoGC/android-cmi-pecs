package com.cmi.presentation.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cmi.presentation.R
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.ktx.getUriFromPath
import com.cmi.presentation.ktx.isTrue
import com.cmi.presentation.ktx.orFalse
import com.cmi.presentation.model.PictureModel

@Composable
fun PictureSelectableItem(
    isLoading: Boolean,
    pictureModel: PictureModel,
    onItemSelected: (pictureModel: PictureModel) -> Unit
) {

    PictureShimmerItem(
        isLoading = isLoading,
        contentAfterLoading = {
            PictureSelectableItemContent(
                pictureModel = pictureModel,
                onItemSelected = onItemSelected
            )
        }
    )
}

@Composable
fun PictureSelectableItemContent(
    modifier: Modifier = Modifier,
    pictureModel: PictureModel,
    onItemSelected: (pictureModel: PictureModel) -> Unit
) {

    val cardViewConfig = CardViewConfig(
        size = dimensionResource(id = R.dimen.picture_card_size),
    )

    PictureCardView(
        modifier = modifier,
        cardViewConfig = cardViewConfig,
        pictureModel = pictureModel,
        onItemSelected = onItemSelected
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ){
            if(pictureModel.isSelectedForUiEnabled.isTrue) {
                RadioButton(
                    selected = pictureModel.isSelected.orFalse,
                    onClick = {

                    },
                    modifier = modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .padding(top = 12.dp, end = 12.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(id = R.color.colorSecondary),
                        unselectedColor = MaterialTheme.colors.onSurface,
                        disabledColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                    )
                )
            }

            Column(
                modifier = modifier
                    .fillMaxWidth().align(alignment = Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultVerticalSpacer(height = 4.dp)
                Image(
                    modifier = modifier.size(
                        if(pictureModel.isSelectedForUiEnabled.isTrue) cardViewConfig.size * 0.5f
                        else cardViewConfig.size * 0.75f
                    ),
                    painter = rememberAsyncImagePainter(getUriFromPath(pictureModel)),
                    contentDescription = pictureModel.name.orEmpty(),
                )
                DefaultVerticalSpacer(height = 2.dp)
                Text(
                    modifier = modifier,
                    text = pictureModel.name.orEmpty(),
                    fontSize = if(pictureModel.isSelectedForUiEnabled.isTrue) 12.sp else 14.sp
                )
                DefaultVerticalSpacer(height = 4.dp)
            }
        }
    }
}
