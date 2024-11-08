package com.cmi.presentation.pecs.pictogram

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.cmi.presentation.ktx.centerHorizontallyFromParentTo
import com.cmi.presentation.ktx.centerHorizontallyToParent

@SuppressLint("ModifierFactoryExtensionFunction")
fun ConstraintLayoutScope.applyPictogramPecsFlowCarouselConstraints(
    pictogramCarousel: ConstrainedLayoutReference,
    titleSpacer: ConstrainedLayoutReference
): Modifier {
    return Modifier.constrainAs(pictogramCarousel){
        this@constrainAs.centerHorizontallyToParent()
        top.linkTo(titleSpacer.bottom)
    }.padding(horizontal = 32.dp)
}

@SuppressLint("ModifierFactoryExtensionFunction")
fun ConstraintLayoutScope.applyTapePecsFlowConstraints(
    tape: ConstrainedLayoutReference,
    topReference: ConstrainedLayoutReference
): Modifier {
    return Modifier.constrainAs(tape){
        top.linkTo(topReference.bottom)
        bottom.linkTo(parent.bottom)
        linkTo(
            start = parent.start,
            end = parent.end,
            top = topReference.bottom,
            bottom = parent.bottom,
            verticalBias = 0.5f
        )
    }.padding(horizontal = 32.dp)
}