package com.cmi.presentation.config.add

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
import com.cmi.presentation.ktx.fullLinkToBottom
import com.cmi.presentation.ktx.fullLinkToTop


@SuppressLint("ModifierFactoryExtensionFunction")
fun ConstraintLayoutScope.applyTitleConstraints(title: ConstrainedLayoutReference): Modifier {
    return Modifier.constrainAs(title) {
        this@constrainAs.fullLinkToTop()
    }
}


@SuppressLint("ModifierFactoryExtensionFunction")
fun ConstraintLayoutScope.applyTitleSpacerConstraints(
    titleSpacer: ConstrainedLayoutReference,
    title: ConstrainedLayoutReference
): Modifier {
    return Modifier.constrainAs(titleSpacer){
        this@constrainAs.centerHorizontallyToParent()
        top.linkTo(title.bottom)
    }
}

// TODO: Fix sizing from resources
@SuppressLint("ModifierFactoryExtensionFunction")
fun ConstraintLayoutScope.applyPictureNameConstraints(
    pictureName: ConstrainedLayoutReference,
    middleGuideline: ConstraintLayoutBaseScope.VerticalAnchor,
    titleSpacer: ConstrainedLayoutReference
): Modifier {
    return Modifier.constrainAs(pictureName){
        this@constrainAs.centerHorizontallyFromParentTo(middleGuideline)
        top.linkTo(titleSpacer.bottom)
    }.padding(horizontal = 32.dp).width(312.dp)
}

@SuppressLint("ModifierFactoryExtensionFunction")
fun ConstraintLayoutScope.applyPictureNameSpacerConstraints(
    pictureNameSpacer: ConstrainedLayoutReference,
    middleGuideline: ConstraintLayoutBaseScope.VerticalAnchor,
    pictureName: ConstrainedLayoutReference
): Modifier {
    return Modifier.constrainAs(pictureNameSpacer){
        this@constrainAs.centerHorizontallyFromParentTo(middleGuideline)
        top.linkTo(pictureName.bottom)
    }
}

@SuppressLint("ModifierFactoryExtensionFunction")
fun ConstraintLayoutScope.applySubmitButtonConstraints(
    submitButton: ConstrainedLayoutReference,
): Modifier {
    return Modifier.constrainAs(submitButton){
        fullLinkToBottom()
    }
}

@SuppressLint("ModifierFactoryExtensionFunction")
fun ConstraintLayoutScope.applyPictureImageResourcesConstraints(
    pictureImageSources: ConstrainedLayoutReference,
    middleGuideline: ConstraintLayoutBaseScope.VerticalAnchor,
    pictureNameSpacer: ConstrainedLayoutReference
): Modifier {
    return Modifier.constrainAs(pictureImageSources){
        this@constrainAs.centerHorizontallyFromParentTo(middleGuideline)
        top.linkTo(pictureNameSpacer.bottom)
    }
}