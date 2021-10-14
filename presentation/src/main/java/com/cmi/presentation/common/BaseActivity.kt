package com.cmi.presentation.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmi.presentation.di.PresentationComponentProvider
import com.cmi.presentation.di.activity.ActivityComponent
import timber.log.Timber

open class BaseActivity : AppCompatActivity(){

    val activityComponent : ActivityComponent by lazy{
        (application as PresentationComponentProvider).provideActivityComponent().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}