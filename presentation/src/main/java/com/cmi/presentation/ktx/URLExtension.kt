package com.cmi.presentation.ktx


import androidx.compose.ui.platform.UriHandler

fun openURL(uriHandler: UriHandler, url: String) {
    uriHandler.openUri(url)
}

