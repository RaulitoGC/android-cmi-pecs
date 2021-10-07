package com.cmi.presentation.di

import com.cmi.presentation.di.activity.ActivityComponent
import com.cmi.presentation.di.fragment.FragmentComponent

interface PresentationComponentProvider {
    fun provideActivityComponent(): ActivityComponent
}