package com.cmi.presentation.survey

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.cmi.presentation.R

@Composable
fun SurveyScreen() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.img_author),
                contentDescription = stringResource(id = R.string.content_description_img_author)
            )
            Text(text = stringResource(id = R.string.text_author_presentation))
        }
    }
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