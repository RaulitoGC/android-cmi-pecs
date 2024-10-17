package com.cmi.presentation.config.category.select

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.components.category.CategorySelectableItem
import com.cmi.presentation.config.add.component.PictureLoaderSelectableToolbar
import com.cmi.presentation.config.add.component.PictureLoaderSubTitle
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.ui.theme.CmiAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectCategoryForPecs(
    viewModel: SelectCategoriesForPecsViewModel = koinViewModel(),
) {

        CmiAppTheme {
            val state = viewModel.uiState.collectAsState().value
            SelectCategoryForPecsContent(
                modifier = Modifier.fillMaxSize(),
                state = state,
                handleEvent = viewModel::handleEvent
            )
        }
}

@Composable
fun SelectCategoryForPecsContent(
    modifier: Modifier = Modifier,
    state: SelectCategoriesForPecsState,
    handleEvent: (event: SelectCategoriesForPecsEvent) -> Unit
) {

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colors.background)
    ) {

        PictureLoaderSelectableToolbar(
            titleConfig = state.titleConfig,
            onUpdate = {
                handleEvent(SelectCategoriesForPecsEvent.UpdateCategories)
            }
        )

        DefaultVerticalSpacer()

        PictureLoaderSubTitle(subTitle = R.string.text_select_category_for_pecs_description)

        DefaultVerticalSpacer(height = 8.dp)

        CategorySelectableGrid(
            state = state,
            onItemSelected = { isSelected, categoryId ->
                handleEvent(
                    SelectCategoriesForPecsEvent.CategorySelection(
                        isSelected = isSelected,
                        categoryId = categoryId
                    )
                )
            }
        )

        val context = LocalContext.current
        LaunchedEffect(state.showSuccess) {
            state.showSuccess?.let {
                Toast.makeText(context, R.string.text_select_categories_success, Toast.LENGTH_SHORT).show()
                handleEvent(SelectCategoriesForPecsEvent.SuccessMessageShown)
            }
        }

        LaunchedEffect(state.showError) {
            state.showError?.let {
                Toast.makeText(context, R.string.text_generic_error, Toast.LENGTH_SHORT).show()
                handleEvent(SelectCategoriesForPecsEvent.ErrorMessageShown)
            }
        }
    }
}

@Composable
fun CategorySelectableGrid(
    state: SelectCategoriesForPecsState,
    onItemSelected: (isSelected: Boolean, categoryId: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(16.dp),
        content = {
            val categories = state.categories
            val size = state.getCategoriesSize()
            items(size) { index ->
                CategorySelectableItem(
                    isLoading = state.isLoading,
                    categoryModel = categories.getOrNull(index)?.copy(
                        isSelectedUiEnabled = true
                    ),
                    onItemSelected = onItemSelected
                )
            }
        }
    )
}