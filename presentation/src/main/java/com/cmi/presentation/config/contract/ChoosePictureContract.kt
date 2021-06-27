package com.cmi.presentation.config.contract

import android.content.Context
import android.content.Intent

object ChoosePictureContract {

    private const val SELECT_FILE_REQUEST_CODE = 125

    private const val IMAGE_GENERIC_TYPE = "image/*"
    private const val IMAGE_PNG_MIME_TYPE = "image/png"
    private const val IMAGE_JPEG_MIME_TYPE = "image/jpeg"
    private const val IMAGE_JPG_MIME_TYPE = "image/jpg"
    private val PICTURE_MIME_TYPES = arrayOf(
        IMAGE_PNG_MIME_TYPE,
        IMAGE_JPEG_MIME_TYPE,
        IMAGE_JPG_MIME_TYPE
    )

    fun getPictureMimeTypes() = PICTURE_MIME_TYPES
    fun getRequestCode() = SELECT_FILE_REQUEST_CODE

    fun resolveActivity(context: Context): Boolean {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = IMAGE_GENERIC_TYPE
        return (intent.resolveActivity(context.packageManager) != null)
    }
}