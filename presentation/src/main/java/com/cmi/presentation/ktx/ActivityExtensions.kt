package com.cmi.presentation.ktx

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.FileProvider
import com.cmi.presentation.R
import com.cmi.presentation.manager.FileProviderManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


fun Activity.showNavigateToSettingsAlert(requestCode: Int = 201) {
    MaterialAlertDialogBuilder(this)
        .setTitle(getString(R.string.text_title_permission_permanently_denied))
        .setMessage(getString(R.string.text_message_permission_permanently_denied))
        .setPositiveButton(getString(R.string.text_navigate_to_settings)) { dialog, _ ->
            dialog.dismiss()
            openSettings(requestCode)
        }
        .setNegativeButton(getString(R.string.text_cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        .setCancelable(false)
        .show()
}

fun Activity.showMessage(message: String) {
    if (message.isNotEmpty()) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

inline fun Activity.takePictureIntent(dispatch: Intent.() -> Unit, action: (absolutePath: String) -> Unit) {
    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
        takePictureIntent.resolveActivity(packageManager)?.also {
            val photoFile: File? = try {
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmm", Locale.US).format(Date())
                val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                storageDir?.let {
                    File.createTempFile(
                        "JPEG_${timeStamp}_", /* prefix */
                        ".jpg", /* suffix */
                        storageDir /* directory */
                    )
                }
            } catch (ex: IOException) {
                showMessage(getString(R.string.text_generic_error))
                null
            }

            photoFile?.also {
                action(it.absolutePath)
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    FileProviderManager.AUTHORITY,
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            }
        }
    }.dispatch()
}

inline fun Activity.selectFileIntent(dispatch: Intent.() -> Unit, mimeTypes: Array<String> = arrayOf()) {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)

    intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
    if (mimeTypes.isNotEmpty()) {
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
    }

    if (intent.resolveActivity(packageManager) != null) {
        Intent.createChooser(intent, "ChooseFile").dispatch()
    }
}

inline fun Activity.settingsIntent(dispatch: Intent.() -> Unit) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    intent.dispatch()
}

fun Activity.openSettings(requestCode: Int) {
    settingsIntent {
        startActivityForResult(this, requestCode)
    }
}