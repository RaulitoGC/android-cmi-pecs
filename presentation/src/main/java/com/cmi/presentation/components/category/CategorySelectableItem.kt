package com.cmi.presentation.components.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.cmi.presentation.R
import com.cmi.presentation.components.common.PictureCardView
import com.cmi.presentation.components.common.PictureShimmerItem
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.ktx.getUriFromPath
import com.cmi.presentation.model.CategoryModel

@Composable
fun CategorySelectableItem(
    isLoading: Boolean,
    categoryModel: CategoryModel?,
    onItemSelected: (Int) -> Unit
) {

    PictureShimmerItem(
        isLoading = isLoading,
        contentAfterLoading = {
            categoryModel?.let {
                CategorySelectableItemContent(
                    categoryModel = categoryModel,
                    onItemSelected = onItemSelected
                )
            }
        }
    )
}

@Composable
fun CategorySelectableItemContent(
    modifier: Modifier = Modifier,
    categoryModel: CategoryModel,
    onItemSelected: (Int) -> Unit
) {

    val cardViewConfig = CardViewConfig(
        size = dimensionResource(id = R.dimen.picture_card_size),
    )

    PictureCardView(
        modifier = modifier,
        cardViewConfig = cardViewConfig,
        categoryModel = categoryModel,
        onItemSelected = onItemSelected
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultVerticalSpacer(4.dp)
            Image(
                modifier = modifier
                    .weight(0.8f),
                painter = rememberAsyncImagePainter(getUriFromPath(categoryModel)),
                contentDescription = categoryModel.name.orEmpty(),
            )
            DefaultVerticalSpacer(height = 2.dp)
            Text(
                modifier = modifier
                    .weight(0.2f),
                text = categoryModel.name.orEmpty()
            )
            DefaultVerticalSpacer(4.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_CategorySelectable() {
    CategorySelectableItemContent(
        categoryModel = CategoryModel(
            categoryId = 1,
            name = "Category 1",
            folder = null,
            path = null,
            priority = 1,
            isExternal = false,
            isSelected = false
        ),
        onItemSelected = {}
    )
}