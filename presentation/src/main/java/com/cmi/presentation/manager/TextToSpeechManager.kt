package com.cmi.presentation.manager

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.core.os.bundleOf
import timber.log.Timber
import java.util.*

class TextToSpeechManager(context: Context) {

    companion object {
        private const val LANGUAGE = "es"
        private const val COUNTRY = "ES"

        private const val TTS_PITCH = 1.0f
        private const val TTS_SPEECH_RATE = 1.0f
        private const val TTS_QUEUE_MODE = TextToSpeech.QUEUE_FLUSH
        private const val TTS_AUDIO_STREAM = TextToSpeech.Engine.DEFAULT_STREAM
    }

    private var textToSpeech: TextToSpeech? = null
    private var listener: TextToSpeech.OnInitListener? = null
    private var loaded = false
    private var pendingSound = false
    private var pendingTextToSpeech = ""

    init {
        listener = TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                loaded = true
                textToSpeech?.language = Locale(LANGUAGE, COUNTRY)
                if (pendingSound) {
                    speak(pendingTextToSpeech)
                }
            }
        }
        textToSpeech = TextToSpeech(context, listener, "com.google.android.tts")
        textToSpeech?.setPitch(TTS_PITCH)
        textToSpeech?.setSpeechRate(TTS_SPEECH_RATE)
        val voice = textToSpeech?.defaultVoice
        if (voice != null) {
            textToSpeech?.voice = voice
        }
        pendingSound = false
        pendingTextToSpeech = ""
    }

    fun speak(message: String) {
        val bundle = bundleOf(TextToSpeech.Engine.KEY_PARAM_STREAM to TTS_AUDIO_STREAM.toString())
        val utteranceId = UUID.randomUUID().toString()
        val emitSound = {
            textToSpeech?.speak(message, TTS_QUEUE_MODE, bundle, utteranceId)
        }

        val response: Int? = emitSound()

        if (response == TextToSpeech.ERROR) {

            pendingSound = true
            pendingTextToSpeech = message
        } else if (response == TextToSpeech.SUCCESS) {
            pendingSound = false
            pendingTextToSpeech = ""
        }
    }

    fun shutdown() {
        textToSpeech?.shutdown()
        listener = null
        textToSpeech = null
    }
}