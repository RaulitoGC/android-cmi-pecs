package com.cmi.data.local.preferences

import android.content.SharedPreferences
import androidx.core.content.edit

class SurveyPreferences(private val sharedPreferences: SharedPreferences) {

    companion object{
        const val SURVEY_FIRST_DATE_KEY = "com.cmi.data.local.preferences.SURVEY_FIRST_DATE_KEY"
        const val SURVEY_BUTTON_TIMES_CLICKED_KEY = "com.cmi.data.local.preferences.SURVEY_BUTTON_TIMES_CLICKED_KEY"
    }

    fun setFirstDateOfSurvey(timeInMillisecond: Long) = sharedPreferences.edit{
        putLong(SURVEY_FIRST_DATE_KEY, timeInMillisecond)
    }
    fun getFirstDateOfSurvey() = sharedPreferences.getLong(SURVEY_FIRST_DATE_KEY, 0L)

    fun incrementSurveyButtonClicked() = sharedPreferences.edit {
        val actualTimes = getSurveyButtonClickedTimes()
        putLong(SURVEY_BUTTON_TIMES_CLICKED_KEY, actualTimes + 1)
    }
    fun getSurveyButtonClickedTimes() = sharedPreferences.getLong(SURVEY_BUTTON_TIMES_CLICKED_KEY, 0L)

}