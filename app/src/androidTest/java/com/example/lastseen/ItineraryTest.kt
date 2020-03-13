package com.example.lastseen

import android.content.Intent
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.*
import org.junit.Before

import org.junit.Rule
import org.junit.Test

class ItineraryTest {
    @Rule
    @JvmField
    val itineraryTestRule = ActivityTestRule(Itinerary::class.java, false, false)

    private val submitButton = onView(withId(R.id.itinerary_submit_button))
    private val trailDropdown = onView(withId(R.id.trail_dropdown))
    private val hourDropdown = onView(withId(R.id.hour_dropdown))
    private val minuteDropdown = onView(withId(R.id.minute_dropdown))
    private val ampmDropdown = onView(withId(R.id.am_pm_dropdown))
    private val updateInformation = onView(withId(R.id.update_information))

    @Before
    fun initBundle() {
        val intent = Intent()

        intent.putExtra("userIdentifier", "bqlmFPzO7lX2dbeRZ8yBugl2c4u2")
        itineraryTestRule.launchActivity(intent)
    }

    @Test
    fun submitItinerary() {
        trailDropdown.perform(click())
        onData(anything()).atPosition(1).perform(click())

        hourDropdown.perform(click())
        onData(anything()).atPosition(5).perform(click())

        minuteDropdown.perform(click())
        onData(anything()).atPosition(2).perform(click())

        ampmDropdown.perform(click())
        onData(anything()).atPosition(1).perform(click())

        submitButton.perform(click())

        Thread.sleep(5000)

        onData(anything()).
            inAdapterView(withId(R.id.itinerary_list)).
            atPosition(0).
            check(matches(isDisplayed()))
    }

    @Test
    fun updateInformationToolbar() {
        Intents.init()
        updateInformation.perform(click())

        intended(hasComponent(UpdateInformation::class.java.name))
        Intents.release()
    }
}