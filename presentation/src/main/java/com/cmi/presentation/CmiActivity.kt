package com.cmi.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.cmi.presentation.common.navigation.Navigation
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.components.uploader.PictureUploader
import com.cmi.presentation.components.uploader.PictureUploaderViewModel
import com.cmi.presentation.manager.TextToSpeechManager
import com.cmi.presentation.ui.theme.CmiAppTheme

class CmiActivity : AppCompatActivity() {

    private lateinit var textToSpeechManager: TextToSpeechManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_cmi)
        setContent {
//            CmiAppTheme {
//                PictureUploader(
//                    contentType = PictureUploaderContentType.PictogramEntry,
//                    onBack = {
//
//                    }
//                )
//            }

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