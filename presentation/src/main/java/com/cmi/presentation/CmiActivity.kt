package com.cmi.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.cmi.presentation.components.common.add.PictureLoaderContentType
import com.cmi.presentation.config.add.PictureLoader
import com.cmi.presentation.manager.TextToSpeechManager

class CmiActivity : AppCompatActivity() {

    private lateinit var textToSpeechManager: TextToSpeechManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            PictureLoader(
                contentType = PictureLoaderContentType.CategoryImage,
            )
        }
        //setContentView(R.layout.activity_cmi)
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