package com.cmi.presentation.components.category

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cmi.presentation.config.add.component.PictureLoaderSubTitle
import com.cmi.presentation.config.add.component.PictureLoaderTitle
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategorySelectable(
    categorySelectableViewModel: CategorySelectableViewModel = koinViewModel(),
    @StringRes title: Int,
    @StringRes subTitle: Int,
    onItemSelected: (Int) -> Unit
) {

    MaterialTheme {
        val state = categorySelectableViewModel.uiState.collectAsState().value
        CategorySelectableContent(
            modifier = Modifier.fillMaxSize(),
            title = title,
            subTitle = subTitle,
            state = state,
            onItemSelected = onItemSelected
        )
    }
}

@Composable
fun CategorySelectableContent(
    modifier: Modifier = Modifier,
    state: CategorySelectableState,
    @StringRes title: Int,
    @StringRes subTitle: Int,
    onItemSelected: (Int) -> Unit
) {

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colors.background)
    ) {

        PictureLoaderTitle(title = title)

        DefaultVerticalSpacer()

        PictureLoaderSubTitle(subTitle = subTitle)

        DefaultVerticalSpacer()

        CategorySelectableGrid(
            modifier = modifier,
            state = state,
            onItemSelected = onItemSelected
        )
    }
}

@Composable
fun CategorySelectableGrid(
    modifier: Modifier,
    state: CategorySelectableState,
    onItemSelected: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        content = {
            val categories = state.categories
            items(categories.size) { item ->
                val category = categories[item]
                CategorySelectableItem(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp),
                    isLoading = state.isLoading,
                    categoryModel = category,
                    onItemSelected = onItemSelected
                )
            }
        }
    )
}

