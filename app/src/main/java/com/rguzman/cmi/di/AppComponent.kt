package com.rguzman.cmi.di

import com.cmi.presentation.di.activity.ActivityComponent
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newActivityComponentBuilder(): ActivityComponent.Builder
}