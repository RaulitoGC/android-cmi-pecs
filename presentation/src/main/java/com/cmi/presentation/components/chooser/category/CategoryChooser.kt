package com.cmi.presentation.components.chooser.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.cmi.presentation.R
import com.cmi.presentation.common.navigation.CategoryChooserHost
import com.cmi.presentation.components.chooser.PictureChooserEvent
import com.cmi.presentation.components.common.PictureSelectableItem
import com.cmi.presentation.components.common.button.NextButton
import com.cmi.presentation.components.common.button.PreviousButton
import com.cmi.presentation.components.common.header.DefaultDescription
import com.cmi.presentation.components.common.header.DefaultTitle
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.getActionCategoryOrEmpty
import com.cmi.presentation.model.getAttributeCategoryOrEmpty
import com.cmi.presentation.model.getNotActionOrAttributeCategoryOrEmpty
import com.cmi.presentation.model.getOrEmpty
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryChooser(
    categoryChooserHost: CategoryChooserHost,
    viewModel: CategoryChooserViewModel = koinViewModel {
        parametersOf(categoryChooserHost)
    },
    onBack: () -> Unit,
    onItemSelected: (categoryModel: CategoryModel) -> Unit
) {

    val state = viewModel.uiState.collectAsState().value
    CategorySelectableContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onBackClick = onBack,
        handleEvent = viewModel::handleEvent,
        onItemSelected = onItemSelected
    )
}

@Composable
fun CategorySelectableContent(
    modifier: Modifier = Modifier,
    state: CategoryChooserState,
    onBackClick: () -> Unit,
    handleEvent: (event: PictureChooserEvent) -> Unit,
    onItemSelected: (categoryModel: CategoryModel) -> Unit
) {

    LifecycleResumeEffect(Unit) {
        handleEvent(PictureChooserEvent.Reload)
        onPauseOrDispose {

        }
    }

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colors.background)
    ) {
        DefaultTitle(
            title = state.categoryChooserHost.title,
            onBackClick = onBackClick
        )

        DefaultVerticalSpacer(height = 8.dp)

        DefaultDescription(description = R.string.text_select_category)

        CategorySelectableGrid(
            state = state,
            onItemSelected = onItemSelected
        )
    }
}

@Composable
fun CategorySelectableGrid(
    modifier: Modifier = Modifier,
    state: CategoryChooserState,
    onItemSelected: (categoryModel: CategoryModel) -> Unit
) {

    if (state.showAsHorizontalGrid()) {
        CategoryChooserHorizontalGrid(state, onItemSelected)
    } else {
        CategoryChooserVerticalGrid(modifier, state, onItemSelected)
    }
}

@Composable
private fun CategoryChooserVerticalGrid(
    modifier: Modifier = Modifier,
    state: CategoryChooserState,
    onItemSelected: (categoryModel: CategoryModel) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(16.dp),
        content = {
            val categories = state.categories
            val size = state.getCategoriesSize()
            items(size) { index ->
                PictureSelectableItem(
                    isLoading = state.isLoading,
                    pictureModel = categories.getOrEmpty(index),
                    cardViewConfig = state.cardViewConfig,
                    onItemSelected = { pictureModel ->
                        if (pictureModel !is CategoryModel) return@PictureSelectableItem
                        onItemSelected(pictureModel)
                    }
                )
            }
        }
    )
}

@Composable
private fun CategoryChooserHorizontalGrid(
    state: CategoryChooserState,
    onItemSelected: (categoryModel: CategoryModel) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        val coroutineScope = rememberCoroutineScope()
        val lazyGridState = rememberLazyGridState()
        val currentItem = remember { mutableIntStateOf(0) }

        val categories = state.getNonActionNorAttributeCategories()
        val size = state.getNonActionNorAttributeCategoriesSize()

        PreviousButton {
            if (currentItem.intValue > 0) {
                currentItem.intValue--
                coroutineScope.launch {
                    lazyGridState.animateScrollToItem(currentItem.intValue)
                }
            }
        }

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .align(alignment = Alignment.CenterVertically)
        ) {
            PictureSelectableItem(
                isLoading = state.isLoading,
                pictureModel = state.categories.getActionCategoryOrEmpty(state.isLoading),
                cardViewConfig = state.cardViewConfig,
                onItemSelected = { pictureModel ->
                    if (pictureModel !is CategoryModel) return@PictureSelectableItem
                    onItemSelected(pictureModel)
                }
            )

            PictureSelectableItem(
                isLoading = state.isLoading,
                pictureModel = state.categories.getAttributeCategoryOrEmpty(state.isLoading),
                cardViewConfig = state.cardViewConfig,
                onItemSelected = { pictureModel ->
                    if (pictureModel !is CategoryModel) return@PictureSelectableItem
                    onItemSelected(pictureModel)
                }
            )
        }

        LazyHorizontalGrid(
            modifier = Modifier.weight(0.6f),
            rows = GridCells.Fixed(2),
            state = lazyGridState,
            contentPadding = PaddingValues(16.dp),
            content = {
                items(size) { index ->
                    PictureSelectableItem(
                        isLoading = state.isLoading,
                        pictureModel = categories.getNotActionOrAttributeCategoryOrEmpty(index),
                        cardViewConfig = state.cardViewConfig,
                        onItemSelected = { pictureModel ->
                            if (pictureModel !is CategoryModel) return@PictureSelectableItem
                            onItemSelected(pictureModel)
                        }
                    )
                }
            }
        )

        NextButton {
            if (currentItem.intValue < categories.size - 1) {
                currentItem.intValue++
                coroutineScope.launch {
                    lazyGridState.animateScrollToItem(currentItem.intValue)
                }
            }
        }
    }
}
