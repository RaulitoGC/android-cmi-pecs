package com.cmi.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.cmi.presentation.manager.TextToSpeechManager
import androidx.compose.material3.Text

class CmiActivity : AppCompatActivity() {

    private lateinit var textToSpeechManager: TextToSpeechManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cmi)
        setContent {
            Text("Hello World")
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