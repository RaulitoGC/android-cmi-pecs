package com.cmi.presentation.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.waterfall
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.cmi.presentation.R
import com.cmi.presentation.components.common.add.DefaultButton
import com.cmi.presentation.components.common.image.DefaultIcon
import com.cmi.presentation.components.common.image.DefaultImage
import com.cmi.presentation.ui.theme.CmiThemeExtensions

@Composable
fun IntroScreen(
    onSettingsSelected: () -> Unit,
    onSurveySelected: () -> Unit,
    onStartPecsFlow: () -> Unit,
    onOpenGuide: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .background(CmiThemeExtensions.colors.primarySurface)
    ) {

        val middleGuideline = createGuidelineFromTop(0.5f)
        val (bottomImage, iconSettings, iconSurvey, imageBoy) = createRefs()
        val (title, subtitle, startButton, guideButton) = createRefs()

        DefaultIcon(
            modifier = applyIconSettingsConstraints(iconSettings)
                .clickable {
                    onSettingsSelected()
                },
            drawableRes = R.drawable.ic_settings
        )

        DefaultIcon(
            modifier = applyIconSurveyConstraints(
                iconSurvey = iconSurvey,
                iconSettings = iconSettings
            ).clickable {
                onSurveySelected()
            },
            drawableRes = R.drawable.ic_survey
        )

        DefaultImage(
            modifier = applyImageBoyConstraints(imageBoy, middleGuideline),
            drawableRes = R.drawable.ic_cmi_boy,
        )

        Text(
            modifier = applyIntroTitleConstraints(title, imageBoy),
            text = stringResource(R.string.text_app_short_name),
            style = CmiThemeExtensions.typography.h1,
            fontSize = 32.sp,
            color = CmiThemeExtensions.colors.primaryText
        )

        Text(
            modifier = applyIntroSubTitleConstraints(subtitle, title),
            text = stringResource(R.string.text_app_long_name),
            style = CmiThemeExtensions.typography.body,
            fontSize = 16.sp,
            color = CmiThemeExtensions.colors.primaryText
        )

        applyHorizontalChainForButtons(startButton, guideButton)

        DefaultButton(
            modifier = applyStartButtonConstraints(startButton, guideButton, subtitle),
            text = R.string.text_start,
        ) {
            onStartPecsFlow()
        }

        DefaultButton(
            modifier = applyGuideButtonConstraints(guideButton, startButton, subtitle),
            text = R.string.text_guide
        ) {
            onOpenGuide()
        }

        DefaultImage(
            modifier = applyBottomImageConstraints(bottomImage, middleGuideline),
            drawableRes = R.drawable.ic_puzzle,
        )
    }

}