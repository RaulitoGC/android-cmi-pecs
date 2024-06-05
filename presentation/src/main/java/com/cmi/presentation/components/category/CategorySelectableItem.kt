package com.cmi.presentation.components.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.cmi.presentation.R
import com.cmi.presentation.components.common.PictureShimmerItem
import com.cmi.presentation.ktx.getUriFromPath
import com.cmi.presentation.ktx.shimmerEffect
import com.cmi.presentation.model.CategoryModel

@Composable
fun CategorySelectableItem(
    modifier: Modifier,
    isLoading: Boolean,
    categoryModel: CategoryModel,
    onItemSelected: (Int) -> Unit
) {
    PictureShimmerItem(
        modifier = modifier,
        isLoading = isLoading,
        contentAfterLoading = {
            CategorySelectableItemContent(
                modifier = modifier,
                categoryModel = categoryModel,
                onItemSelected = onItemSelected
            )
        }
    )
}

@Composable
fun CategorySelectableItemContent(
    modifier: Modifier,
    categoryModel: CategoryModel,
    onItemSelected: (Int) -> Unit
){

    val width = dimensionResource(id = R.dimen.picture_preview_size_width)
    val height = dimensionResource(id = R.dimen.picture_preview_size_height)

    Card(
        modifier = modifier
            .size(
                width = width,
                height = height
            ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, colorResource(id = R.color.colorPictogramBorder)),
        elevation = 8.dp
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(getUriFromPath(categoryModel)),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.content_description_img_author),
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 32.dp)
                    .fillMaxWidth()
                    .height(height - 40.dp)
                    .border(2.dp, colorResource(id = R.color.colorPrimaryDark), CircleShape)
            )
            Text(
                text = categoryModel.name.orEmpty(),
                modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}