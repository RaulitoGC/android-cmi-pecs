package com.cmi.presentation.manager

import android.Manifest
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.cmi.presentation.R
import com.cmi.presentation.ktx.MultiplePermissionsDelegate
import com.cmi.presentation.ktx.showMessage
import com.cmi.presentation.ktx.showNavigateToSettingsAlert
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport

object DexterManager {

    fun requestCameraPermission(context: Context, listener: () -> Unit) {
        Dexter.withContext(context)
            .withPermissions(Manifest.permission.CAMERA)
            .withListener(object : MultiplePermissionsDelegate() {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report?.areAllPermissionsGranted() == true) {
                        listener()
                    }
                    if (report?.isAnyPermissionPermanentlyDenied == true) {
                        (context as FragmentActivity).showNavigateToSettingsAlert()
                    }
                }
            })
            .onSameThread()
            .withErrorListener { (context as FragmentActivity).showMessage(context.getString(R.string.text_generic_error)) }
            .check()
    }

    fun requestStoragePermissions(context: Context, listener: () -> Unit) {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsDelegate() {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report?.areAllPermissionsGranted() == true) {
                        listener()
                    }
                    if (report?.isAnyPermissionPermanentlyDenied == true) {
                        (context as FragmentActivity).showNavigateToSettingsAlert()
                    }
                }
            })
            .onSameThread()
            .withErrorListener { (context as FragmentActivity).showMessage(context.getString(R.string.text_generic_error)) }
            .check()
    }
}