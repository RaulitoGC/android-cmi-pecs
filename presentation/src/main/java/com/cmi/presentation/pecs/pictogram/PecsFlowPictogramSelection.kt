package com.cmi.presentation.pecs.pictogram

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.cmi.presentation.R
import com.cmi.presentation.components.common.PictureCardView
import com.cmi.presentation.components.common.PictureSelectableItem
import com.cmi.presentation.components.common.applyTitleConstraints
import com.cmi.presentation.components.common.applyVerticalSpacerConstraints
import com.cmi.presentation.components.common.button.NextButton
import com.cmi.presentation.components.common.button.PreviousButton
import com.cmi.presentation.components.common.header.DefaultTitle
import com.cmi.presentation.components.common.image.DefaultImage
import com.cmi.presentation.content.CardViewConfig
import com.cmi.presentation.ktx.DefaultVerticalSpacer
import com.cmi.presentation.ktx.getUriFromPath
import com.cmi.presentation.ktx.isTrue
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.PictureModel
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

        TapePecsFlow(
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

@Composable
private fun TapePecsFlow(
    modifier: Modifier = Modifier,
    cardViewConfig: CardViewConfig,
    handleEvent: (event: PecsFlowPictogramSelectionEvent) -> Unit,
    picturesForPecs: List<PictogramModel>
) {
    Row(
        modifier = modifier
    ) {
        ViewStripPhrase(
            modifier = Modifier
                .weight(0.85f)
                .align(alignment = Alignment.CenterVertically),
            handleEvent = handleEvent,
            picturesForPecs = picturesForPecs,
            cardViewConfig = cardViewConfig
        )
        DefaultImage(
            modifier = Modifier
                .weight(0.15f)
                .clickable {
                    handleEvent(PecsFlowPictogramSelectionEvent.ExecuteSound)
                },
            drawableRes = R.drawable.ic_volume_up,
        )
    }
}

@Composable
fun ViewStripPhrase(
    modifier: Modifier = Modifier,
    picturesForPecs: List<PictogramModel>,
    cardViewConfig: CardViewConfig,
    handleEvent: (event: PecsFlowPictogramSelectionEvent) -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.pecs_stripe_size_height))
                .padding(start = 16.dp, end = 16.dp)
                .background(
                    color = CmiThemeExtensions.colors.primaryColor,
                    shape = RoundedCornerShape(
                        dimensionResource(R.dimen.stripe_view_corner_radius)
                    )
                )
        ) {

        }

        picturesForPecs.filter { it.id != null }.forEach { pictogramModel ->
            PictogramCloseableItemContent(
                pictogramModel = pictogramModel,
                cardViewConfig = cardViewConfig,
                onItemSelected = {

                }
            )
        }
    }
}

@Composable
fun PictogramCloseableItemContent(
    modifier: Modifier = Modifier,
    pictogramModel: PictogramModel,
    cardViewConfig: CardViewConfig,
    onItemSelected: (pictureModel: PictureModel) -> Unit
) {

    PictureCardView(
        modifier = modifier,
        cardViewConfig = cardViewConfig,
        pictureModel = pictogramModel,
        onItemSelected = onItemSelected
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultVerticalSpacer(height = 4.dp)

                val imageSize = dimensionResource(cardViewConfig.imageSize)
                val fontSize = dimensionResource(cardViewConfig.fontSize)

                Image(
                    modifier = modifier.size(
                        if (pictogramModel.isSelectedForUiEnabled.isTrue) imageSize * 0.9f
                        else imageSize
                    ),
                    painter = rememberAsyncImagePainter(getUriFromPath(pictogramModel)),
                    contentDescription = pictogramModel.name.orEmpty(),
                )
                DefaultVerticalSpacer(height = 2.dp)
                Text(
                    modifier = modifier,
                    text = pictogramModel.name.orEmpty(),
                    fontSize = if (pictogramModel.isSelectedForUiEnabled.isTrue) fontSize.value.sp else ((fontSize.value + 4).sp)
                )
                DefaultVerticalSpacer(height = 4.dp)
            }
        }
    }
}