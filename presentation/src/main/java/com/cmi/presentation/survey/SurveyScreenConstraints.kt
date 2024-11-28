package com.cmi.presentation.survey

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.cmi.presentation.R

@Composable
fun ConstraintLayoutScope.applyImageAuthorConstraints(
    imageAuthor: ConstrainedLayoutReference,
    bottomReference: ConstrainedLayoutReference,
): Modifier {
    return Modifier
        .constrainAs(imageAuthor) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(bottomReference.top)
            top.linkTo(parent.top)
        }.padding(top = 32.dp, bottom = 16.dp)
        .size(dimensionResource(R.dimen.survey_author_size))
        .clip(CircleShape)
        .border(4.dp, colorResource(id = R.color.colorPrimaryDark), CircleShape)
}

@Composable
fun ConstraintLayoutScope.applyPresentationTextConstraints(
    presentationText: ConstrainedLayoutReference,
    bottomReference: ConstraintLayoutBaseScope.HorizontalAnchor,
): Modifier {
    return Modifier
        .constrainAs(presentationText) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(bottomReference)
        }.padding(start = 32.dp, end = 32.dp)
}

@Composable
fun ConstraintLayoutScope.applySurveyContainerConstraints(
    surveyContainer: ConstrainedLayoutReference,
    topReference: ConstraintLayoutBaseScope.HorizontalAnchor,
): Modifier {
    return Modifier
        .constrainAs(surveyContainer) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            top.linkTo(topReference)
        }.padding(start = 32.dp, end = 32.dp, top = 16.dp)
}

@Composable
fun ConstraintLayoutScope.applyCloseButtonConstraints(
    closeButton: ConstrainedLayoutReference
): Modifier {
    return Modifier
        .size(84.dp)
        .padding(WindowInsets.systemBars.asPaddingValues())
        .constrainAs(closeButton) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
}