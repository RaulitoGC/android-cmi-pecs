package com.rguzmanc.cmi

import timber.log.Timber

class TimberLoggingTree : Timber.DebugTree(){

    override fun createStackElementTag(element: StackTraceElement): String {
        with(element) {
            return "($fileName:$lineNumber)$methodName()"
        }
    }
}