package com.cmi.presentation.components.selecter

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.components.common.header.DefaultDescription
import com.cmi.presentation.components.selecter.type.PictureSelecterContentType
import com.cmi.presentation.config.category.common.PictureSelectableItem
import com.cmi.presentation.components.common.header.PictureSelectableToolbar
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.model.getOrEmpty

@Composable
fun PictureSelecterForPecs(
    pictureSelecterContentType: PictureSelecterContentType,
    viewModel: PictureSelecterForPecsViewModel = PictureSelecterForPecsViewModel.create(pictureSelecterContentType),
    onBack: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    SelectCategoryForPecsContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onBack = onBack,
        handleEvent = viewModel::handleEvent
    )
}

@Composable
fun SelectCategoryForPecsContent(
    modifier: Modifier = Modifier,
    state: PictureSelecterForPecsState,
    onBack: () -> Unit,
    handleEvent: (event: PictureSelecterForPecsEvent) -> Unit
) {

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colors.background)
    ) {

        PictureSelectableToolbar(
            titleConfig = state.titleConfig,
            onBackClick = onBack,
            onUpdate = {
                handleEvent(PictureSelecterForPecsEvent.UpdatePictures)
            }
        )

        DefaultVerticalSpacer()

        DefaultDescription(description = R.string.text_select_category_for_pecs_description)

        DefaultVerticalSpacer(height = 8.dp)

        CategorySelectableGrid(
            state = state,
            onItemSelected = { pictureModel ->
                handleEvent(
                    PictureSelecterForPecsEvent.PictureSelected(
                        pictureModel = pictureModel
                    )
                )
            }
        )

        val context = LocalContext.current

        state.showSuccessToastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            handleEvent(PictureSelecterForPecsEvent.SuccessMessageShown)
            onBack()
        }

        state.showErrorToastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            handleEvent(PictureSelecterForPecsEvent.ErrorMessageShown)
        }
    }
}

@Composable
fun CategorySelectableGrid(
    state: PictureSelecterForPecsState,
    onItemSelected: (pictureModel: PictureModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(16.dp),
        content = {
            val pictureModels = state.pictureModels
            val size = state.getPicturesSize()
            items(size) { index ->
                PictureSelectableItem(
                    isLoading = state.isLoading,
                    pictureModel = pictureModels.getOrEmpty(index),
                    onItemSelected = onItemSelected
                )
            }
        }
    )
}