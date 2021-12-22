package com.cmi.data.local.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CmiPreferenceTest {

    companion object {
        const val SHARED_PREFERENCE_FAKE_NAME = "CMI_PECS_SHARED_PREFERENCE_FAKE_NAME"
    }

    private lateinit var context: Context
    private lateinit var cmiPreferences: CmiPreferences

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCE_FAKE_NAME, MODE_PRIVATE)
        cmiPreferences = CmiPreferences(sharedPreferences)
    }

    @Test
    fun `validate main pictogram id`() {
        val mainPictogramId = 2
        cmiPreferences.setPictogramMainId(mainPictogramId)
        assert(cmiPreferences.getPictogramMainId() == mainPictogramId)
    }

    @Test
    fun `validate first action id`() {
        val firstActionId = 2
        cmiPreferences.setPictogramFirstActionId(firstActionId)
        assert(cmiPreferences.getPictogramFirstActionId() == firstActionId)
    }

    @Test
    fun `validate second action id`() {
        val secondActionId = 2
        cmiPreferences.setPictogramSecondActionId(secondActionId)
        assert(cmiPreferences.getPictogramSecondActionId() == secondActionId)
    }

    @Test
    fun `validate pictogram attribute action id`() {
        val pictogramAttributeId = 2
        cmiPreferences.setPictogramAttributeId(pictogramAttributeId)
        assert(cmiPreferences.getPictogramAttributeId() == pictogramAttributeId)
    }

    @Test
    fun `validate  second pictogram attribute action id`() {
        val secondPictogramAttributeId = 2
        cmiPreferences.setSecondPictogramAttributeId(secondPictogramAttributeId)
        assert(cmiPreferences.getSecondPictogramAttributeId() == secondPictogramAttributeId)
    }
}
