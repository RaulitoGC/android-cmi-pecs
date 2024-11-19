package com.cmi.presentation

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.cmi.presentation.common.navigation.Navigation
import com.cmi.presentation.manager.TextToSpeechManager

class CmiActivity : AppCompatActivity() {

    private lateinit var textToSpeechManager: TextToSpeechManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(scrim = getColor(R.color.colorPrimaryDark)),
        )
        setContent {

            Navigation()
        }
        textToSpeechManager = TextToSpeechManager(this)
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
