package com.cmi.presentation.ktx

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.VerticalAnchorable

fun ConstrainScope.fullLinkToTop() {
    centerHorizontallyToParent()
    top.linkTo(parent.top)
}

fun ConstrainScope.centerHorizontallyToParent() {
    start.linkTo(parent.start)
    end.linkTo(parent.end)
}

fun ConstrainScope.centerHorizontallyFromParentTo(to: ConstraintLayoutBaseScope.VerticalAnchor) {
    start.linkTo(parent.start)
    end.linkTo(to)
}

fun ConstrainScope.centerHorizontallyToParentFrom(from: ConstraintLayoutBaseScope.VerticalAnchor) {
    start.linkTo(from)
    end.linkTo(parent.end)
}

fun ConstrainScope.fullLinkToBottom() {
    centerHorizontallyToParent()
    bottom.linkTo(parent.bottom)
}
