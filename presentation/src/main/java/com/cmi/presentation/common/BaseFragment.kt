package com.cmi.presentation.common

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cmi.presentation.CmiActivity
import com.cmi.presentation.di.activity.ActivityComponent
import com.cmi.presentation.di.fragment.FragmentComponent
import javax.inject.Inject

open class BaseFragment : Fragment(){

    private val fragmentComponent: FragmentComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.getFragmentComponent()
    }

    protected val injector = fragmentComponent

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

}