package com.cmi.data.local.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import timber.log.Timber

class CmiPreferences(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val PICTOGRAM_MAIN_ID = "com.cmi.data.local.preferences.PICTOGRAM_MAIN_ID"
        const val PICTOGRAM_FIRST_ACTION_ID =
            "com.cmi.data.local.preferences.PICTOGRAM_FIRST_ACTION_ID"
        const val PICTOGRAM_SECOND_ACTION_ID =
            "com.cmi.data.local.preferences.PICTOGRAM_SECOND_ACTION_ID"
        const val PICTOGRAM_ATTRIBUTE_ID =
            "com.cmi.data.local.preferences.PICTOGRAM_ATTRIBUTE_ID"

        const val PICTOGRAM_SECOND_ATTRIBUTE_ID = "com.cmi.data.local.preferences.PICTOGRAM_SECOND_ATTRIBUTE_ID"

        const val PICTOGRAM_INVALID_ID = -1
    }

    fun getPictogramMainId(): Int =
        sharedPreferences.getInt(PICTOGRAM_MAIN_ID, PICTOGRAM_INVALID_ID)

    fun setPictogramMainId(pictogramId: Int) = sharedPreferences.edit {
        putInt(PICTOGRAM_MAIN_ID, pictogramId)
    }

    fun getPictogramFirstActionId() =
        sharedPreferences.getInt(PICTOGRAM_FIRST_ACTION_ID, PICTOGRAM_INVALID_ID)

    fun setPictogramFirstActionId(pictogramId: Int) = sharedPreferences.edit {
        putInt(PICTOGRAM_FIRST_ACTION_ID, pictogramId)
    }

    fun getPictogramSecondActionId() =
        sharedPreferences.getInt(PICTOGRAM_SECOND_ACTION_ID, PICTOGRAM_INVALID_ID)

    fun setPictogramSecondActionId(pictogramId: Int) = sharedPreferences.edit {
        putInt(PICTOGRAM_SECOND_ACTION_ID, pictogramId)
    }

    fun getPictogramAttributeId() =
        sharedPreferences.getInt(PICTOGRAM_ATTRIBUTE_ID, PICTOGRAM_INVALID_ID)

    fun setPictogramAttributeId(pictogramId: Int) = sharedPreferences.edit {
        putInt(PICTOGRAM_ATTRIBUTE_ID, pictogramId)
    }

    fun getSecondPictogramAttributeId() = sharedPreferences.getInt(PICTOGRAM_SECOND_ATTRIBUTE_ID, PICTOGRAM_INVALID_ID)

    fun setSecondPictogramAttributeId(pictogramId: Int) = sharedPreferences.edit {
        putInt(PICTOGRAM_SECOND_ATTRIBUTE_ID, pictogramId)
    }
}