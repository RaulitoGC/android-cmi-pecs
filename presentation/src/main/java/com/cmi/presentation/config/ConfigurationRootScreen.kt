package com.cmi.presentation.config

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cmi.presentation.R
import com.cmi.presentation.components.common.title.DefaultTitle
import com.cmi.presentation.config.add.component.PictureLoaderSubTitle
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun ConfigurationRootScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onItemSelected: (flow: FLOW) -> Unit
) {
    Column {
        DefaultTitle(
            title = R.string.text_settings,
            onBackClick = onBack
        )
        DefaultVerticalSpacer(height = 8.dp)
        PictureLoaderSubTitle(subTitle = R.string.text_configuration_option_message)
        DefaultVerticalSpacer(height = 8.dp)
        Row {
            ConfigurationTypeCard(
                modifier = modifier
                    .weight(0.5f)
                    .padding(vertical = 16.dp, horizontal = 32.dp),
                image = R.drawable.img_category,
                title = stringResource(id = R.string.text_configuration_category),
                onItemSelected = { onItemSelected(FLOW.CATEGORY) }
            )
            ConfigurationTypeCard(
                modifier = modifier
                    .weight(0.5f)
                    .padding(vertical = 16.dp, horizontal = 32.dp),
                image = R.drawable.img_pictogram,
                title = stringResource(id = R.string.text_configuration_pictogram),
                onItemSelected = { onItemSelected(FLOW.PICTOGRAM) }
            )
        }
    }
}

// RGC: Pass text style to this composable instead of textFontSize
@Composable
fun ConfigurationTypeCard(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    title: String,
    textFontSize: TextUnit = 24.sp,
    onItemSelected: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_view_corner_radius)),
        border = BorderStroke(
            dimensionResource(id = R.dimen.card_view_border_stroke),
            colorResource(id = R.color.colorPictogramBorder)
        ),
        elevation = dimensionResource(id = R.dimen.card_view_elevation),
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.margin_4dp))
            .border(
                border = BorderStroke(
                    dimensionResource(id = R.dimen.card_view_border_stroke),
                    colorResource(id = R.color.colorPictogramBorder)
                ),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_view_corner_radius))
            )
            .clickable(onClick = onItemSelected)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultVerticalSpacer(modifier = Modifier.weight(0.07f), height = 4.dp)
            Image(
                modifier = Modifier
                    .weight(0.7f),
                painter = rememberAsyncImagePainter(image),
                contentDescription = title,
            )
            DefaultVerticalSpacer(modifier = Modifier.weight(0.01f), height = 0.dp)
            Text(
                text = title,
                modifier = Modifier
                    .weight(0.15f),
                textAlign = TextAlign.Center,
                color = CmiThemeExtensions.colors.primaryColorDark,
                style = CmiThemeExtensions.typography.body,
                fontSize = textFontSize,
            )
            DefaultVerticalSpacer(modifier = Modifier.weight(0.07f), height = 4.dp)
        }
    }
}

