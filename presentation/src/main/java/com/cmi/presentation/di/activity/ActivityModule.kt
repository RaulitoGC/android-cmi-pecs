package com.cmi.presentation.di.activity

import android.content.Context
import com.cmi.presentation.manager.TextToSpeechManager
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @Provides
    @ActivityScope
    fun provideTextToSpeechManager(context: Context) = TextToSpeechManager(context)
}