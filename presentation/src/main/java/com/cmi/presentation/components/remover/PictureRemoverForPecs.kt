package com.cmi.presentation.components.remover

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cmi.presentation.R
import com.cmi.presentation.components.common.alert.DefaultAlertDialog
import com.cmi.presentation.components.common.header.DefaultDescription
import com.cmi.presentation.components.remover.type.PictureRemoverContentType
import com.cmi.presentation.components.common.header.PictureSelectableToolbar
import com.cmi.presentation.components.common.PictureSelectableItem
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.model.PictureModel
import com.cmi.presentation.model.getOrEmpty

@Composable
fun PictureRemoverForPecs(
    contentType: PictureRemoverContentType,
    viewModel: PictureRemoverForPecsViewModel = PictureRemoverForPecsViewModel.create(contentType),
    onBack: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    PictureRemoverForPecsContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onBack = onBack,
        handleEvent = viewModel::handleEvent
    )
}

@Composable
fun PictureRemoverForPecsContent(
    modifier: Modifier = Modifier,
    state: PictureRemoverState,
    onBack: () -> Unit,
    handleEvent: (event: PictureRemoverEvent) -> Unit
) {

    Column(
        modifier = modifier
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .background(color = MaterialTheme.colors.background)
    ) {

        PictureSelectableToolbar(
            titleConfig = state.titleConfig,
            actionTitle = R.string.text_delete,
            onBackClick = onBack,
            onUpdate = {
                handleEvent(PictureRemoverEvent.RemovePictures)
            }
        )

        DefaultVerticalSpacer()

        DefaultDescription(description = R.string.text_select_category_for_pecs_description)

        DefaultVerticalSpacer(height = 8.dp)

        PictureRemoverGrid(
            state = state,
            onItemSelected = { pictureModel ->
                handleEvent(
                    PictureRemoverEvent.PictureSelectedForRemoval(
                        pictureModel = pictureModel
                    )
                )
            }
        )

        val context = LocalContext.current

        state.showToastMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            handleEvent(PictureRemoverEvent.ToastMessageShown)
            onBack()
        }

        state.showAlertMessage?.let {
            DefaultAlertDialog(
                description = it,
                onDismiss = {
                    handleEvent(PictureRemoverEvent.AlertMessageShown)
                    onBack()
                }
            )
        }
    }
}

@Composable
fun PictureRemoverGrid(
    state: PictureRemoverState,
    onItemSelected: (pictureModel: PictureModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(16.dp),
        content = {
            val pictures = state.pictureModels
            val size = state.getPictureSize()
            items(size) { index ->
                PictureSelectableItem(
                    isLoading = state.isLoading,
                    pictureModel = pictures.getOrEmpty(index),
                    cardViewConfig = state.cardViewConfig,
                    onItemSelected = onItemSelected
                )
            }
        }
    )
}