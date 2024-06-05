package com.cmi.presentation.survey

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmi.presentation.Constants
import com.cmi.presentation.R
import com.cmi.presentation.ktx.openURL

@Composable
fun SurveyScreen() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_author),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.content_description_img_author),
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 32.dp)
                    .size(96.dp)
                    .clip(CircleShape)
                    .border(2.dp, colorResource(id = R.color.colorPrimaryDark), CircleShape)
            )
            Text(
                text = stringResource(id = R.string.text_author_presentation),
                modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                textAlign = TextAlign.Center
            )
            Row(modifier = Modifier
                .padding(start = 32.dp, end = 32.dp, top = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    val localUriHandler = LocalUriHandler.current
                    Text(
                        text = stringResource(id = R.string.text_title_start_survey)
                    )
                    Text(
                        text = stringResource(id = R.string.text_message_start_survey),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = {
                            openStartSurvey(localUriHandler)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.text_btn_start_survey))
                    }
                }
                Divider(
                    color = Color.Transparent,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(10.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    val localUriHandler = LocalUriHandler.current
                    Text(
                        text = stringResource(id = R.string.text_title_end_survey)
                    )
                    Text(
                        text = stringResource(id = R.string.text_message_end_survey),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { openEndSurvey(localUriHandler)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.text_btn_end_survey))
                    }
                }
            }
        }
    }
}

private fun openStartSurvey(uriHandler: UriHandler){
    openURL(uriHandler, Constants.END_SURVEY_URL)
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
fun SurveyScreenPreview(){
    SurveyScreen()
}