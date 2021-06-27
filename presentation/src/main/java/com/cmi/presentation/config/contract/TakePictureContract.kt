package com.cmi.presentation.config.contract

import android.content.Context
import android.content.Intent
import android.provider.MediaStore

object TakePictureContract {

    private const val TAKE_PHOTO_REQUEST_CODE = 123

    fun getRequestCode() = TAKE_PHOTO_REQUEST_CODE

    fun resolveActivity(context: Context): Boolean {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        return (intent.resolveActivity(context.packageManager) != null)
    }
}