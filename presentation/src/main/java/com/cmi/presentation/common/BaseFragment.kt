package com.cmi.presentation.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cmi.presentation.CmiActivity

open class BaseFragment : Fragment(){

    private val fragmentComponent by lazy {
        (requireActivity() as CmiActivity).activityComponent.getFragmentComponent()
    }

    protected val injector = fragmentComponent

}