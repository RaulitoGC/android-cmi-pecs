package com.cmi.presentation.ktx

import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

abstract class MultiplePermissionsDelegate : MultiplePermissionsListener {
    override fun onPermissionRationaleShouldBeShown(
        permissions: MutableList<PermissionRequest>?,
        token: PermissionToken?
    ) {
        token?.continuePermissionRequest()
    }
}