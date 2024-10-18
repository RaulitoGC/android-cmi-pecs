package com.cmi.presentation.utils

import androidx.annotation.StringRes

class MessageBuilder()


sealed class MessageType {
    data class Success(
        @StringRes val message: Int
    ) : MessageType()
    object Error : MessageType()
    object Info : MessageType()
    object Warning : MessageType()
}