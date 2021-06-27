package com.cmi.presentation.ktx

import android.os.SystemClock
import android.view.View

const val clickInterval = 100

var lastTimeClicked: Long = 0

class SafeClickListener(
    private val customInterval: Int = clickInterval,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < customInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

fun View.setSafeOnClickListener(customInterval: Int = clickInterval, onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener(customInterval) {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}