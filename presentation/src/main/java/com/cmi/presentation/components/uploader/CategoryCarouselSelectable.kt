package com.cmi.presentation.components.uploader

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.components.common.PictureSelectableItem
import com.cmi.presentation.components.common.button.NextButton
import com.cmi.presentation.components.common.button.PreviousButton
import com.cmi.presentation.components.common.header.DefaultSubTitle
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.ui.theme.CmiThemeExtensions
import kotlinx.coroutines.launch

@Composable
fun CategoryCarouselSelectable(
    modifier: Modifier = Modifier,
    items: List<CategoryModel>,
    cardViewConfig: CardViewConfig,
    onItemSelected: (categoryModel: CategoryModel) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),

        ) {
        DefaultSubTitle(
            subTitle = R.string.text_select_category,
            padding = 0.dp
        )

        DefaultVerticalSpacer()

        val coroutineScope = rememberCoroutineScope()
        val state = rememberLazyListState()
        val currentItem = remember { mutableIntStateOf(0) }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            PreviousButton {
                if (currentItem.intValue > 0) {
                    currentItem.intValue--
                    coroutineScope.launch {
                        state.animateScrollToItem(currentItem.intValue)
                    }
                }
            }

            LazyRow(
                modifier = Modifier.weight(0.8f),
                state = state
            ) {

                items(items.size) { index ->
                    val categoryModel = items[index]
                    PictureSelectableItem(
                        isLoading = false,
                        pictureModel = categoryModel,
                        cardViewConfig = cardViewConfig,
                        onItemSelected = { pictureModel ->
                            if (pictureModel !is CategoryModel) return@PictureSelectableItem
                            onItemSelected(pictureModel)
                        }
                    )
                }
            }

            NextButton {
                if (currentItem.intValue < items.size - 1) {
                    currentItem.intValue++
                    coroutineScope.launch {
                        state.animateScrollToItem(currentItem.intValue)
                    }
                }
            }

        }
    }
}
