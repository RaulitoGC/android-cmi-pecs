package com.cmi.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import com.cmi.presentation.R

class MessageBuilder(
    private val context: Context
) {
    fun build(messageType: MessageType): String {
        return when (messageType) {
            is MessageType.Success -> context.getString(messageType.message)
            is MessageType.GeneralError -> context.getString(R.string.text_generic_error)
            is MessageType.Error -> context.getString(messageType.message)
        }
    }
}



sealed class MessageType {
    data class Success(
        @StringRes val message: Int
    ) : MessageType()
    data class Error(
        @StringRes val message: Int
    ) : MessageType()
    data object GeneralError : MessageType()
}