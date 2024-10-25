package com.cmi.presentation.components.common

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.cmi.presentation.ktx.centerHorizontallyToParent
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
    return Modifier.constrainAs(titleSpacer) {
        this@constrainAs.centerHorizontallyToParent()
        top.linkTo(title.bottom)
    }
}
