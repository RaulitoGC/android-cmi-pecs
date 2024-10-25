package com.cmi.presentation.ktx

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShowToast(message: String) {
    val context = LocalContext.current
    if(message.isNotEmpty()) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}