package com.cmi.presentation

import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cmi.presentation.intro.IntroFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntroFragmentTest {

//    @get:Rule
//    var activityRule: ActivityScenarioRule<CmiActivity> =
//        ActivityScenarioRule(CmiActivity::class.java)

    @Test
    fun testEventFragment() {

        val scenario = launchFragmentInContainer<IntroFragment>(themeResId = R.style.CmiTheme)

        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.btn_start))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_guide))
            .check(matches(isDisplayed()))

    }
}