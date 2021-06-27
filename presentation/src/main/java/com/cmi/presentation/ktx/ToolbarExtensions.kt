package com.cmi.presentation.ktx

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.cmi.presentation.R

inline fun Toolbar.setUpNavigation(@DrawableRes navIcon: Int? = null, crossinline action: () -> Unit) {
    val navId = navIcon ?: R.drawable.ic_arrow_back
    navigationIcon = ContextCompat.getDrawable(context, navId)
    setNavigationOnClickListener { action() }
}