package com.cmi.data.local.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CmiPreferenceTest {

    private lateinit var context: Context
    private lateinit var cmiPreferences: CmiPreferences

    @Before
    fun setUp(){
        context = ApplicationProvider.getApplicationContext()
        val sharedPreferences = context.getSharedPreferences("fake", MODE_PRIVATE)
        cmiPreferences = CmiPreferences(sharedPreferences)
    }

    @Test
    fun `validate set pictogram id`() {
        val pictogramId = 2
        cmiPreferences.setPictogramMainId(pictogramId)
        assert(cmiPreferences.getPictogramMainId() == pictogramId)
    }

}