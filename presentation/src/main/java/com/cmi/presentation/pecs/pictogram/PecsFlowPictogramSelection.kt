package com.cmi.presentation.pecs.pictogram

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import com.cmi.presentation.R
import com.cmi.presentation.components.common.PictureSelectableItem
import com.cmi.presentation.components.common.applyTitleConstraints
import com.cmi.presentation.components.common.applyVerticalSpacerConstraints
import com.cmi.presentation.components.common.button.NextButton
import com.cmi.presentation.components.common.button.PreviousButton
import com.cmi.presentation.components.common.header.DefaultTitle
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.ui.theme.CmiThemeExtensions
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PecsFlowPictogramSelection(
    categoryId: Int,
    viewModel: PecsFlowPictogramSelectionViewModel = koinViewModel {
        parametersOf(categoryId)
    },
    onBack: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    PecsFlowPictogramSelectionContent(
        state = state,
        handleEvent = viewModel::handleEvent,
        onBack = onBack
    )
}

@Composable
private fun PecsFlowPictogramSelectionContent(
    state: PecsFlowPictogramSelectionState,
    handleEvent: (event: PecsFlowPictogramSelectionEvent) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    state.showMessage?.let { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        handleEvent(PecsFlowPictogramSelectionEvent.MessageShown)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .background(CmiThemeExtensions.colors.windowBackground)
            .verticalScroll(rememberScrollState())
    ) {

        val (title, pictogramCarousel, tape) = createRefs()
        val (titleSpacer, carouselSpacer) = createRefs()

        DefaultTitle(
            modifier = applyTitleConstraints(title),
            title = R.string.text_toolbar_pecs_flow_pictogram_selection,
            onBackClick = onBack
        )

        DefaultVerticalSpacer(
            modifier = applyVerticalSpacerConstraints(titleSpacer, title)
        )

        PictogramPecsFlowCarousel(
            modifier = applyPictogramPecsFlowCarouselConstraints(pictogramCarousel, titleSpacer),
            items = state.pictograms,
            cardViewConfig = state.cardViewConfig,
            onItemSelected = { pictogramSelected ->
                handleEvent(PecsFlowPictogramSelectionEvent.OnPictogramSelected(pictogramSelected))
            }
        )

        DefaultVerticalSpacer(
            modifier = applyVerticalSpacerConstraints(carouselSpacer, pictogramCarousel)
        )

        ViewStripPhrase(
            modifier = applyTapePecsFlowConstraints(tape, carouselSpacer),
            cardViewConfig = state.cardViewConfig,
            handleEvent = handleEvent,
            picturesForPecs = state.pictogramsForPecs
        )

    }
}

@Composable
private fun PictogramPecsFlowCarousel(
    modifier: Modifier = Modifier,
    items: List<PictogramModel>,
    cardViewConfig: CardViewConfig,
    onItemSelected: (pictogramModel: PictogramModel) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        val coroutineScope = rememberCoroutineScope()
        val lazyRowState = rememberLazyListState()
        val currentItem = remember { mutableIntStateOf(0) }

        PreviousButton {
            if (currentItem.intValue > 0) {
                currentItem.intValue--
                coroutineScope.launch {
                    lazyRowState.animateScrollToItem(currentItem.intValue)
                }
            }
        }

        LazyRow(
            modifier = Modifier.weight(0.8f),
            state = lazyRowState
        ) {

            items(items.size) { index ->
                val pictogramModel = items[index]
                PictureSelectableItem(
                    isLoading = false,
                    pictureModel = pictogramModel,
                    cardViewConfig = cardViewConfig,
                    onItemSelected = { pictureModel ->
                        if (pictureModel !is PictogramModel) return@PictureSelectableItem
                        onItemSelected(pictureModel)
                    }
                )
            }
        }

        NextButton {
            if (currentItem.intValue < items.size - 1) {
                currentItem.intValue++
                coroutineScope.launch {
                    lazyRowState.animateScrollToItem(currentItem.intValue)
                }
            }
        }
    }
}

