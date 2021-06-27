package com.cmi.presentation.ktx

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictogramModel

const val ANDROID_ASSET_PATH = "file:///android_asset"

fun ImageView.loadImage(data: CategoryModel) {
    if (data.isExternal == true) {
        Glide.with(context).load(data.path).into(this)
    } else {
        val folder = data.folder
        val path = data.path
        val absolutePath = "$ANDROID_ASSET_PATH/$folder/$path"
        Glide.with(context).load(Uri.parse(absolutePath)).into(this)
    }
}

fun ImageView.loadImage(data: PictogramModel) {
    if (data.isExternal == true) {

        Glide.with(context).load(data.path).into(this)

    } else {
        val folder = data.folder
        val path = data.path
        val absolutePath = "$ANDROID_ASSET_PATH/$folder/$path"
        Glide.with(context).load(Uri.parse(absolutePath)).into(this)
    }
}