package com.cmi.presentation.ktx

import android.content.res.Resources
import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictureModel
import java.io.IOException


fun PictureModel.getImagePath(): String? {
    val fullPath = path
    if(path.isNullOrEmpty()){
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
