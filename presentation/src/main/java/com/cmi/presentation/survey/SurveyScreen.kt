package com.cmi.presentation.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.cmi.presentation.Constants
import com.cmi.presentation.R
import com.cmi.presentation.components.common.add.DefaultButton
import com.cmi.presentation.components.common.image.DefaultImage
import com.cmi.presentation.ktx.openURL
import com.cmi.presentation.ui.theme.CmiAppTheme
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
@NonRestartableComposable
fun SurveyScreen(
    onBack: () -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .background(CmiThemeExtensions.colors.primaryColor)
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {

        val horizontalGuideLine = createGuidelineFromTop(0.6f)
        val (imageAuthor, presentationText, surveyContainer) = createRefs()

        DefaultImage(
            modifier = applyImageAuthorConstraints(
                imageAuthor = imageAuthor,
                bottomReference = presentationText
            ),
            drawableRes = R.drawable.img_author,
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.content_description_img_author),
        )

        Text(
            modifier = applyPresentationTextConstraints(
                presentationText = presentationText,
                bottomReference = horizontalGuideLine
            ),
            text = stringResource(id = R.string.text_author_presentation),
            style = CmiThemeExtensions.typography.body,
            fontSize = dimensionResource(R.dimen.survey_text_size).value.sp,
            color = CmiThemeExtensions.colors.primaryText,
            textAlign = TextAlign.Center
        )


        Row(
            modifier = applySurveyContainerConstraints(
                surveyContainer = surveyContainer,
                topReference = horizontalGuideLine
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                val localUriHandler = LocalUriHandler.current
                Text(
                    text = stringResource(id = R.string.text_title_start_survey),
                    style = CmiThemeExtensions.typography.body,
                    color = CmiThemeExtensions.colors.primaryText,
                    fontSize = dimensionResource(R.dimen.survey_text_size).value.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(id = R.string.text_message_start_survey),
                    textAlign = TextAlign.Center,
                    style = CmiThemeExtensions.typography.body,
                    fontSize = dimensionResource(R.dimen.survey_text_size).value.sp,
                    color = CmiThemeExtensions.colors.primaryText
                )
                DefaultButton(text = R.string.text_btn_start_survey) {
                    openStartSurvey(localUriHandler)
                }
            }
            Divider(
                color = Color.Transparent,
                modifier = Modifier
                    .width(10.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                val localUriHandler = LocalUriHandler.current
                Text(
                    text = stringResource(id = R.string.text_title_end_survey),
                    style = CmiThemeExtensions.typography.body,
                    color = CmiThemeExtensions.colors.primaryText,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(R.dimen.survey_text_size).value.sp,
                )
                Text(
                    text = stringResource(id = R.string.text_message_end_survey),
                    textAlign = TextAlign.Center,
                    fontSize = dimensionResource(R.dimen.survey_text_size).value.sp,
                    style = CmiThemeExtensions.typography.body,
                    color = CmiThemeExtensions.colors.primaryText
                )
                DefaultButton(text = R.string.text_btn_end_survey) {
                    openEndSurvey(localUriHandler)
                }
            }
        }

        DefaultImage(
            modifier = applyCloseButtonConstraints(
                closeButton = createRef()
            ).clickable {
                onBack()
            },
            drawableRes = R.drawable.ic_close,
        )
    }
}

private fun openStartSurvey(uriHandler: UriHandler) {
    openURL(uriHandler, Constants.START_SURVEY_URL)
}

private fun openEndSurvey(uriHandler: UriHandler) {
    openURL(uriHandler, Constants.END_SURVEY_URL)
}

@Preview(
    showBackground = true,
    heightDp = 360,
    widthDp = 800
)
@Composable
fun SurveyScreenPreview() {
    CmiAppTheme {
        SurveyScreen {

        }
    }
}