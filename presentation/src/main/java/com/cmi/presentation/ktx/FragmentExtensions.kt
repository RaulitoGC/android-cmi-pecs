package com.cmi.presentation.ktx

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cmi.presentation.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun Fragment.showMessage(message: String) {
    val context = context
    if (message.isNotEmpty() && context != null) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}

fun Fragment.showAlertMessage(message: String, listener: () -> Unit = {}) {
    activity?.let { activity ->
        MaterialAlertDialogBuilder(activity)
            .setTitle(getString(R.string.text_app_short_name))
            .setMessage(message)
            .setPositiveButton(getString(R.string.text_accept)) { _, _ ->
                listener()
            }
            .setCancelable(false)
            .show()
    }
}

fun Fragment.showAlertMessage(
    title: String,
    message: String,
    positiveButton: String,
    negativeButton: String,
    listener: () -> Unit = {}
) {
    activity?.let { activity ->
        MaterialAlertDialogBuilder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButton) { _, _ ->
                listener()
            }
            .setNegativeButton(negativeButton, null)
            .setCancelable(false)
            .show()
    }
}

fun Fragment.openURL(URL: String) {
    this.context?.openUrl(url = URL)
}