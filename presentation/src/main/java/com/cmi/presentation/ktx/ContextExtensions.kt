package com.cmi.presentation.ktx

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openUrl(url: String){
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}