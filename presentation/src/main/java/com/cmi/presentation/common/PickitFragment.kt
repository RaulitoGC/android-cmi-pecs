package com.cmi.presentation.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks

abstract class PickitFragment: Fragment(), PickiTCallbacks {

    abstract fun onStartLoadingImage()
    abstract fun onProgressUpdate(progress: Int)
    abstract fun onImageLoaded(wasSuccessful: Boolean,path: String?)

    private lateinit var pickiT: PickiT

    protected fun getPickiT() = pickiT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            pickiT = PickiT(context, this@PickitFragment, activity)
        }
    }

    override fun PickiTonUriReturned() {
        onStartLoadingImage()
    }

    override fun PickiTonStartListener() {

    }

    override fun PickiTonProgressUpdate(progress: Int) {
        onProgressUpdate(progress = progress)
    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        onImageLoaded(wasSuccessful = wasSuccessful, path = path)
    }
}