package com.cmi.presentation.manager

import android.content.Context
import androidx.annotation.StringRes

interface StringResourceManager {

    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String
}

class DefaultStringResourceManager(
    val context: Context
) : StringResourceManager {

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getString(resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }
}