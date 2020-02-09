package com.example.lastseen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CreateAccountTest {
    @Rule
    @JvmField
    val createAccountTestRule : ActivityTestRule<CreateAccount> = ActivityTestRule(CreateAccount::class.java)

    private val submit = onView(withId(R.id.submit))
    private val firstName = onView(withId(R.id.FirstName))
    private val lastName = onView(withId(R.id.LastName))
    private val dateOfBirth = onView(withId(R.id.DateofBirth))
    private val streetAddress = onView(withId(R.id.StreetAddress))
    private val city = onView(withId(R.id.City))
    private val state = onView(withId(R.id.State))
    private val zipCode = onView(withId(R.id.ZipCode))
    private val phoneNumber = onView(withId(R.id.PhoneNumber))

    @Test
    fun submitTest() {
        submit.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        firstName.perform(click())
        firstName.perform(typeText("Aaron"))
        firstName.perform(closeSoftKeyboard())
        submit.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        lastName.perform(click())
        lastName.perform(typeText("Baron"))
        lastName.perform(closeSoftKeyboard())
        submit.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        streetAddress.perform(click())
        streetAddress.perform(typeText("3333 Third Road"))
        streetAddress.perform(closeSoftKeyboard())
        submit.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        city.perform(click())
        city.perform(typeText("Twenty"))
        city.perform(closeSoftKeyboard())
        submit.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        state.perform(click())
        state.perform(typeText("RT"))
        state.perform(closeSoftKeyboard())
        submit.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        zipCode.perform(click())
        zipCode.perform(typeText("3333 Third Road"))
        zipCode.perform(closeSoftKeyboard())
        submit.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        phoneNumber.perform(click())
        phoneNumber.perform(typeText("4445559999"))
        phoneNumber.perform(closeSoftKeyboard())
        submit.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        dateOfBirth.perform(click())
        dateOfBirth.perform(typeText("01/12/1890"))
        dateOfBirth.perform(closeSoftKeyboard())
        submit.perform(click())

        assertTrue(createAccountTestRule.activity.isFinishing)
    }
}