package com.cmi.presentation.ktx

import android.net.Uri
import com.bumptech.glide.Glide
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictureModel

fun getUriFromPath(data: PictureModel): Uri? {
    return if (data.isFoundationPath == true) {
        val folder = data.folder
        val path = data.path
        val absolutePath = "$ANDROID_ASSET_PATH/$folder/$path"
        Uri.parse(absolutePath)

    } else {
        Uri.parse(data.path)
    }
}

fun Uri?.toStringOrEmpty(): String {
    return this?.toString() ?: ""
}