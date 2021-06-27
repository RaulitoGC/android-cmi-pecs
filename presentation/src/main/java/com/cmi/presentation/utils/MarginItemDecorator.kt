package com.cmi.presentation.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorator(context: Context, @DimenRes marginInDp: Int) :
    RecyclerView.ItemDecoration() {

    private val marginInPx: Int =
        context.resources.getDimension(marginInDp).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = marginInPx
        outRect.left = marginInPx
        outRect.top = marginInPx
        outRect.bottom = marginInPx
    }
}