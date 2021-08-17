package com.cmi.presentation

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmi.presentation.config.ConfigurationFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfigurationFragmentTest {

    @Test
    fun verifySubtitle() {

        val scenario =
            launchFragmentInContainer<ConfigurationFragment>(themeResId = R.style.CmiTheme)

        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.txt_select_option))
            .check(matches(isDisplayed()))

    }
}