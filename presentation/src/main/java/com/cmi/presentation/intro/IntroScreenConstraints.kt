package com.cmi.presentation.intro

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope.HorizontalAnchor
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.HorizontalChainReference
import com.cmi.presentation.R
import com.cmi.presentation.ktx.fullLinkToBottom

@Composable
fun ConstraintLayoutScope.applyIconSettingsConstraints(
    iconSettings: ConstrainedLayoutReference
): Modifier {
    return Modifier
        .size(dimensionResource(R.dimen.settings_size))
        .constrainAs(iconSettings) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
        .padding(top = 12.dp, end = 12.dp)
}

@Composable
fun ConstraintLayoutScope.applyIconSurveyConstraints(
    iconSurvey: ConstrainedLayoutReference,
    iconSettings: ConstrainedLayoutReference
): Modifier {
    return Modifier
        .size(dimensionResource(R.dimen.settings_size))
        .constrainAs(iconSurvey) {
            top.linkTo(parent.top)
            end.linkTo(iconSettings.start)
        }
        .padding(top = 12.dp, end = 12.dp)
}

@Composable
fun ConstraintLayoutScope.applyImageBoyConstraints(
    imageBoy: ConstrainedLayoutReference,
    middleGuideline: HorizontalAnchor
): Modifier {
    return Modifier
        .size(
            width = dimensionResource(R.dimen.image_boy_width_size),
            height = dimensionResource(R.dimen.image_boy_height_size)
        )
        .constrainAs(imageBoy) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(middleGuideline)
        }
}

@Composable
fun ConstraintLayoutScope.applyIntroTitleConstraints(
    title: ConstrainedLayoutReference,
    imageBoy: ConstrainedLayoutReference
): Modifier {
    return Modifier
        .constrainAs(title) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(imageBoy.bottom)
        }
        .padding(top = 12.dp)
}

@Composable
fun ConstraintLayoutScope.applyIntroSubTitleConstraints(
    subtitle: ConstrainedLayoutReference,
    title: ConstrainedLayoutReference
): Modifier {
    return Modifier
        .constrainAs(subtitle) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(title.bottom)
        }
        .padding(top = 12.dp)
}

@Composable
fun ConstraintLayoutScope.applyStartButtonConstraints(
    startButton: ConstrainedLayoutReference,
    guideButton: ConstrainedLayoutReference,
    subtitle: ConstrainedLayoutReference
): Modifier {
    return Modifier.constrainAs(startButton) {
        start.linkTo(parent.start)
        bottom.linkTo(parent.bottom)
        end.linkTo(guideButton.start)
        top.linkTo(subtitle.bottom)
    }
}

@Composable
fun ConstraintLayoutScope.applyGuideButtonConstraints(
    guideButton: ConstrainedLayoutReference,
    startButton: ConstrainedLayoutReference,
    subtitle: ConstrainedLayoutReference
): Modifier {
    return Modifier
        .constrainAs(guideButton) {
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            start.linkTo(startButton.end)
            top.linkTo(subtitle.bottom)
        }
        .padding(end = 12.dp)
}

@Composable
fun ConstraintLayoutScope.applyBottomImageConstraints(
    bottomImage: ConstrainedLayoutReference,
    middleGuideline: HorizontalAnchor,
): Modifier {
    return Modifier
        .constrainAs(bottomImage) {
            fullLinkToBottom()
            top.linkTo(middleGuideline)
        }
        .padding(start = 12.dp)
}

@Composable
fun ConstraintLayoutScope.applyHorizontalChainForButtons(
    startButton: ConstrainedLayoutReference,
    guideButton: ConstrainedLayoutReference
): HorizontalChainReference{
    return createHorizontalChain(
        elements = arrayOf(
            startButton.withChainParams(endMargin = 12.dp),
            guideButton.withChainParams(startMargin = 12.dp)
        ),
        chainStyle = ChainStyle.Packed
    )
}