package com.cmi.presentation.components.common

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.cmi.presentation.R

@Composable
fun ShowErrorToastMessage() {
    val context = LocalContext.current
    val message = stringResource(id = R.string.text_generic_error)
    LaunchedEffect(key1 = true) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
