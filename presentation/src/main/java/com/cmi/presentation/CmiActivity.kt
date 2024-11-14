package com.cmi.presentation

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.cmi.presentation.common.navigation.Navigation
import com.cmi.presentation.components.common.add.PictureUploaderContentType
import com.cmi.presentation.components.uploader.PictureUploader
import com.cmi.presentation.components.uploader.PictureUploaderViewModel
import com.cmi.presentation.intro.IntroScreen
import com.cmi.presentation.manager.TextToSpeechManager
import com.cmi.presentation.ui.theme.CmiAppTheme

class CmiActivity : AppCompatActivity() {

    private lateinit var textToSpeechManager: TextToSpeechManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_cmi)

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
