package com.cmi.presentation.ktx

import android.net.Uri
import com.bumptech.glide.Glide
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictureModel

fun getUriFromPath(data: PictureModel): Uri? {
    val path = data.path
    if (path.isNullOrEmpty()) {
        return null
    }
    return if (data.isFoundationPath == true) {
        val folder = data.folder
        val absolutePath = "$ANDROID_ASSET_PATH/$folder/$path"
        Uri.parse(absolutePath)
    } else {
        Uri.parse(path)
    }
}

fun Uri?.toStringOrEmpty(): String {
    return this?.toString() ?: ""
}