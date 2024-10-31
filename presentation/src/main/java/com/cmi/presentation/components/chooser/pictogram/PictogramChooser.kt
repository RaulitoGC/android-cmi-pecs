package com.cmi.presentation.components.chooser.pictogram

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.components.common.PictureSelectableItem
import com.cmi.presentation.components.common.header.DefaultDescription
import com.cmi.presentation.components.common.header.DefaultTitle
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.getOrEmpty
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PictogramChooser(
    categoryId: Int,
    viewModel: PictogramChooserViewModel = koinViewModel{
        parametersOf(categoryId)
    },
    onBack: () -> Unit,
    onItemSelected: (pictogramModel: PictogramModel) -> Unit
) {

    val state = viewModel.uiState.collectAsState().value
    PictogramSelectableContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onBackClick = onBack,
        onItemSelected = onItemSelected
    )
}

@Composable
fun PictogramSelectableContent(
    modifier: Modifier = Modifier,
    state: PictogramChooserState,
    onBackClick: () -> Unit,
    onItemSelected: (pictogramModel: PictogramModel) -> Unit
) {

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colors.background)
    ) {
        DefaultTitle(
            title = stringResource(R.string.text_edit_pictogram),
            onBackClick = onBackClick
        )

        DefaultVerticalSpacer(height = 8.dp)

        DefaultDescription(description = R.string.text_select_pictogram)

        DefaultVerticalSpacer(height = 8.dp)

        PictogramSelectableGrid(
            state = state,
            onItemSelected = onItemSelected
        )
    }
}

@Composable
fun PictogramSelectableGrid(
    modifier: Modifier = Modifier,
    state: PictogramChooserState,
    onItemSelected: (pictogramModel: PictogramModel) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(16.dp),
        content = {
            val categories = state.pictograms
            val size = state.getPictogramsSize()
            items(size) { index ->
                PictureSelectableItem(
                    isLoading = state.isLoading,
                    pictureModel = categories.getOrEmpty(index),
                    onItemSelected = { pictureModel ->
                        if (pictureModel !is PictogramModel) return@PictureSelectableItem
                        onItemSelected(pictureModel)
                    }
                )
            }
        }
    )
}
