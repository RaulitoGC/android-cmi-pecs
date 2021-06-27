package com.cmi.presentation.utils

object LoggingManager {

    fun initLogger(){
        try {
            var process = Runtime.getRuntime().exec("logcat -d")
            process = Runtime.getRuntime().exec( "logcat -f " + "/storage/emulated/0/"+"Logging.txt")
        }catch( e: Exception) {
            e.printStackTrace()
        }
    }
}