package com.rguzman.cmi

import android.app.Application
import com.cmi.presentation.di.PresentationComponentProvider
import com.cmi.presentation.di.activity.ActivityComponent
import com.rguzman.cmi.di.AppComponent
import com.rguzman.cmi.di.AppModule
import com.rguzman.cmi.di.DaggerAppComponent
import timber.log.Timber

class CmiApplication : Application(), PresentationComponentProvider {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        Timber.plant(TimberLoggingTree())
    }

    override fun provideActivityComponent(): ActivityComponent.Builder {
        return appComponent.newActivityComponentBuilder()
    }


}