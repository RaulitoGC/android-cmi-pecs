package com.cmi.presentation.ktx

import com.cmi.presentation.model.PictureModel

fun PictureModel.getImagePath(): String? {
    val fullPath = path
    if (path.isNullOrEmpty()) {
        return null
    }

    return if (isFoundationPath == true) {
        val folder = folder
        val absolutePath = "$ANDROID_ASSET_PATH/$folder/$fullPath"
        absolutePath

    } else {
        fullPath
    }
}
