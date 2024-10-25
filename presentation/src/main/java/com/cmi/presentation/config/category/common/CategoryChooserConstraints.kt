package com.cmi.presentation.config.category.common

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.cmi.presentation.ktx.centerHorizontallyToParent
import com.cmi.presentation.ktx.fullLinkToBottom

@SuppressLint("ModifierFactoryExtensionFunction")
fun ConstraintLayoutScope.applySubTitleConstraints(
    subTitle: ConstrainedLayoutReference,
    titleSpacer: ConstrainedLayoutReference
): Modifier {
    return Modifier.constrainAs(subTitle){
        this@constrainAs.centerHorizontallyToParent()
        top.linkTo(titleSpacer.bottom)
    }
}

@SuppressLint("ModifierFactoryExtensionFunction")
fun ConstraintLayoutScope.applyCategorySelectableConstraints(
    categorySelectableGrid: ConstrainedLayoutReference,
    subtitleSpacer: ConstrainedLayoutReference
): Modifier {
    return Modifier.constrainAs(categorySelectableGrid){
        top.linkTo(subtitleSpacer.bottom)
        this@constrainAs.centerHorizontallyToParent()
    }
}
