package com.cmi.presentation.config.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cmi.presentation.R
import com.cmi.presentation.config.add.component.PictureLoaderInformation
import com.cmi.presentation.config.add.component.PictureLoaderSubTitle
import com.cmi.presentation.config.add.component.PictureLoaderSubmit
import com.cmi.presentation.config.add.component.PictureLoaderTitle
import com.cmi.presentation.config.add.model.PictureLoaderEvent
import com.cmi.presentation.config.add.model.PictureLoaderState
import com.cmi.presentation.ktx.DefaultVerticalSpacer

@Composable
fun PictureLoader() {

    val viewModel: PictureLoaderViewModel = viewModel()

    MaterialTheme {
        val state = viewModel.uiState.collectAsState().value
        PictureLoaderContent(
            modifier = Modifier.fillMaxSize(),
            state = state,
            handleEvent = viewModel::handleEvent
        )
    }
}

@Composable
private fun PictureLoaderContent(
    modifier: Modifier = Modifier,
    state: PictureLoaderState,
    handleEvent: (event: PictureLoaderEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colors.background)
    ) {

        PictureLoaderTitle(title = state.contentType.title)

        DefaultVerticalSpacer()

        PictureLoaderSubTitle(subTitle = state.contentType.subTitle)

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(id = R.dimen.margin_4dp),
                    horizontal = dimensionResource(id = R.dimen.margin_high)
                )
        )

        DefaultVerticalSpacer()

        PictureLoaderInformation(
            modifier = modifier,
            state = state,
            handleEvent = handleEvent
        )

        PictureLoaderSubmit(modifier = modifier, state = state)
    }
}

@Preview(
    showBackground = true,
    heightDp = 360,
    widthDp = 800
)
@Composable
fun PictureLoaderScreenPreview() {
    PictureLoader()
}