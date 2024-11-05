package com.cmi.presentation.components.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.cmi.presentation.R
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.ktx.orFalse
import com.cmi.presentation.ktx.orZero
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictureModel

@Composable
fun PictureCardView(
    modifier: Modifier,
    cardViewConfig: CardViewConfig,
    pictureModel: PictureModel,
    onItemSelected: (pictureModel: PictureModel) -> Unit,
    content: @Composable () -> Unit
) {

    val size = dimensionResource(id = cardViewConfig.size)

    Card(
        shape = RoundedCornerShape(dimensionResource(id = cardViewConfig.cornerRadius)),
        border = BorderStroke(
            dimensionResource(id = cardViewConfig.borderStroke),
            colorResource(id = R.color.colorPictogramBorder)
        ),
        elevation = dimensionResource(id = cardViewConfig.elevation),


        modifier = modifier
            .size(size)
            .padding(dimensionResource(id = R.dimen.margin_4dp))
            .border(
                border = BorderStroke(
                    dimensionResource(id = cardViewConfig.borderStroke),
                    colorResource(id = R.color.colorPictogramBorder)
                ),
                shape = RoundedCornerShape(dimensionResource(id = cardViewConfig.cornerRadius))
            )
            .clickable {
                onItemSelected(pictureModel)
            }
    ) {
        content()
    }
}
