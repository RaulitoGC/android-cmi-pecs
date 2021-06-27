package com.cmi.presentation.intro

import android.content.Context
import com.cmi.data.local.preferences.SurveyPreferences
import com.cmi.presentation.R
import java.util.*

class SurveyValidator(
    private val context: Context,
    private val surveyPreferences: SurveyPreferences
) {

    companion object {
        const val TIME_FOR_SECOND_SURVEY = 2629800000L // Month
    }

    enum class SURVEY {
        FIRST, SECOND
    }

    fun showSurveyAlert(): Boolean {
        val isFirstTime = isFirstTimeShowingSurvey()
        return isFirstTimeShowingSurvey() || (!isFirstTime && surveyPreferences.getSurveyButtonClickedTimes() < 3)
    }

    fun registerSurveyButtonClicked() {
        val now = Calendar.getInstance().time.time
        surveyPreferences.setFirstDateOfSurvey(timeInMillisecond = now)
        surveyPreferences.incrementSurveyButtonClicked()
    }

    private fun getSurveyType(): SURVEY {
        return if (isFirstTimeShowingSurvey() || getDifferenceBetweenSurveyDates() < TIME_FOR_SECOND_SURVEY) SURVEY.FIRST else SURVEY.SECOND
    }

    private fun getDifferenceBetweenSurveyDates(): Long{
        val now = Calendar.getInstance().time.time
        val lasTime = surveyPreferences.getFirstDateOfSurvey()
        return now - lasTime
    }

    private fun isFirstTimeShowingSurvey(): Boolean{
        val lastTime = surveyPreferences.getFirstDateOfSurvey()
        return lastTime == 0L
    }

    fun getSurveyTitle(): String {
        return context.getString(R.string.text_survey_title)
    }

    fun getSurveyDescription(): String {
        return when (getSurveyType()) {
            SURVEY.FIRST -> {
                context.getString(R.string.text_first_survey_description)
            }

            SURVEY.SECOND -> {
                context.getString(R.string.text_second_survey_description)
            }
        }
    }
}