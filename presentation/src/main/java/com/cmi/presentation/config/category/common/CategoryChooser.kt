package com.cmi.presentation.config.category.common

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
import androidx.navigation.NavController
import com.cmi.presentation.R
import com.cmi.presentation.common.navigation.CategoryChooserHost
import com.cmi.presentation.common.navigation.navigateToCategoryEdit
import com.cmi.presentation.components.common.title.DefaultTitle
import com.cmi.presentation.config.add.component.PictureLoaderSubTitle
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.ui.theme.CmiAppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryChooser(
    categoryChooserHost: CategoryChooserHost,
    navController: NavController
) {
    val viewModel: CategoryChooserViewModel = koinViewModel {
        parametersOf(categoryChooserHost)
    }

    val state = viewModel.uiState.collectAsState().value
    CategorySelectableContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onBackClick = {
            navController.popBackStack()
        },
        onItemSelected = { categoryModel ->
            when(categoryChooserHost){
                is CategoryChooserHost.EditCategory -> navController.navigateToCategoryEdit(categoryModel)
                else -> {

                }
            }

        }
    )
}

@Composable
fun CategorySelectableContent(
    modifier: Modifier = Modifier,
    state: CategoryChooserState,
    onBackClick: () -> Unit,
    onItemSelected: (categoryModel: CategoryModel) -> Unit
) {

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colors.background)
    ) {
        DefaultTitle(
            title = state.categoryChooserHost.title,
            onBackClick = onBackClick
        )

        DefaultVerticalSpacer(height = 8.dp)

        PictureLoaderSubTitle(subTitle = R.string.text_select_category)

        DefaultVerticalSpacer(height = 8.dp)

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
                CategorySelectableItem(
                    isLoading = state.isLoading,
                    categoryModel = categories.getOrNull(index),
                    onItemSelected = onItemSelected
                )
            }
        }
    )
}