package com.cmi.presentation.components.chooser.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.cmi.presentation.R
import com.cmi.presentation.common.navigation.CategoryChooserHost
import com.cmi.presentation.components.chooser.PictureChooserEvent
import com.cmi.presentation.components.common.header.DefaultDescription
import com.cmi.presentation.components.common.header.DefaultTitle
import com.cmi.presentation.components.common.PictureSelectableItem
import com.cmi.presentation.components.remover.PictureRemoverEvent
import com.cmi.presentation.components.uploader.PictureUploaderEvent
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.getOrEmpty
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
