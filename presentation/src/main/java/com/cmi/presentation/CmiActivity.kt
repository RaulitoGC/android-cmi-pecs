package com.cmi.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmi.presentation.di.PresentationComponentProvider
import com.cmi.presentation.manager.TextToSpeechManager
import javax.inject.Inject

class CmiActivity : AppCompatActivity() {

    val activityComponent = (application as PresentationComponentProvider).provideActivityComponent()

    @Inject lateinit var textToSpeechManager: TextToSpeechManager

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cmi)
    }

    fun provideTextToSpeechManager(): TextToSpeechManager {
        return if (::textToSpeechManager.isInitialized) {
            textToSpeechManager
        } else {
            TextToSpeechManager(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeechManager.shutdown()
    }
}