package com.cmi.presentation.di.activity

import com.cmi.presentation.CmiActivity
import com.cmi.presentation.di.fragment.FragmentComponent
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(cmiActivity: CmiActivity)

    fun getFragmentComponent(): FragmentComponent

    @Subcomponent.Builder
    interface Builder {
        fun build(): ActivityComponent
    }
}