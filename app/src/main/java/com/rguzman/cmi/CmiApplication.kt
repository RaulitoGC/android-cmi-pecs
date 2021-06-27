package com.rguzman.cmi

import android.app.Application
import com.cmi.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class CmiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
        initInjector()
    }

    private fun initInjector() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@CmiApplication)
            modules(presentationModule)
        }
    }

    private fun initLogger() {
        Timber.plant(TimberLoggingTree())
    }
}