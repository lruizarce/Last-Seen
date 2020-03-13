package com.example.lastseen

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpdateInformationTest {
    @Rule
    @JvmField
    val updateInformationTestRule = ActivityTestRule(UpdateInformation::class.java, false, false)

    private val changeNameButton = onView(withId(R.id.change_name_button))
    private val firstNameInput = onView(withId(R.id.first_name_input))
    private val lastNameInput = onView(withId(R.id.last_name_input))
    private val name = onView(withId(R.id.name))

    private val changeAddressButton = onView(withId(R.id.change_address_button))
    private val streetAddressInput = onView(withId(R.id.address_street_input))
    private val cityAddressInput = onView(withId(R.id.address_city_input))
    private val stateAddressInput = onView(withId(R.id.address_state_input))
    private val zipCodeAddressInput = onView(withId(R.id.address_zip_code_input))
    private val address = onView(withId(R.id.address))

    private val changePhoneNumberButton = onView(withId(R.id.change_phone_number_button))
    private val phoneNumberInput = onView(withId(R.id.phone_number_input))
    private val phoneNumber = onView(withId(R.id.phone_number))

    private val changeEmergencyContactButton = onView(withId(R.id.change_emergency_contact_button))
    private val emergencyContactFirstNameInput = onView(withId(R.id.emergency_contact_first_name_input))
    private val emergencyContactLastNameInput = onView(withId(R.id.emergency_contact_last_name_input))
    private val emergencyContactPhoneNumberInput = onView(withId(R.id.emergency_contact_phone_number_input))
    private val emergencyContact = onView(withId(R.id.emergency_contact))

    private val submitButton = onView(withId(R.id.update_information_submit_button))

    @Before
    fun initIntent() {
        val intent = Intent()

        intent.putExtra("userIdentifier", "bqlmFPzO7lX2dbeRZ8yBugl2c4u2")
        updateInformationTestRule.launchActivity(intent)
    }

    @Test
    fun updateNameTest() {
        changeNameButton.perform(click())
        Thread.sleep(1000)
        firstNameInput.perform(clearText())
        firstNameInput.perform(typeText("First"))
        lastNameInput.perform(clearText())
        lastNameInput.perform(typeText("Last"))
        changeNameButton.perform(click())

        Thread.sleep(1000)
        name.check(matches(withText("First Last")))

        submitButton.perform(click())

        Thread.sleep(5000)

        assertTrue(updateInformationTestRule.activity.isFinishing)
    }

    @Test
    fun updateAddressTest() {
        changeAddressButton.perform(click())
        Thread.sleep(1000)
        streetAddressInput.perform(clearText())
        streetAddressInput.perform(typeText("9999 Nine Avenue"))
        cityAddressInput.perform(clearText())
        cityAddressInput.perform(typeText("Triangle"))
        stateAddressInput.perform(clearText())
        stateAddressInput.perform(typeText("ZZ"))
        zipCodeAddressInput.perform(clearText())
        zipCodeAddressInput.perform(typeText("99999"))
        changeAddressButton.perform(click())

        address.check(matches(withText("9999 Nine Avenue\nTriangle, ZZ 99999")))

        submitButton.perform(click())

        Thread.sleep(5000)

        assertTrue(updateInformationTestRule.activity.isFinishing)
    }

    @Test
    fun updatePhoneNumberTest() {
        changePhoneNumberButton.perform(click())
        Thread.sleep(1000)
        phoneNumberInput.perform(clearText())
        phoneNumberInput.perform(typeText("9999999999"))
        changePhoneNumberButton.perform(click())

        phoneNumber.check(matches(withText("9999999999")))

        submitButton.perform(click())

        Thread.sleep(5000)

        assertTrue(updateInformationTestRule.activity.isFinishing)
    }

    @Test
    fun updateEmergencyContactTest() {
        changeEmergencyContactButton.perform(click())
        Thread.sleep(1000)
        emergencyContactFirstNameInput.perform(clearText())
        emergencyContactFirstNameInput.perform(typeText("First"))
        emergencyContactLastNameInput.perform(clearText())
        emergencyContactLastNameInput.perform(typeText("Last"))
        emergencyContactLastNameInput.perform(closeSoftKeyboard())
        emergencyContactPhoneNumberInput.perform(clearText())
        emergencyContactPhoneNumberInput.perform(typeText("8888888888"))
        changeEmergencyContactButton.perform(click())

        emergencyContact.check(matches(withText("First Last\n8888888888")))

        submitButton.perform(click())

        Thread.sleep(5000)

        assertTrue(updateInformationTestRule.activity.isFinishing)
    }

    @Test
    fun submitWithNameTest() {
        changeNameButton.perform(click())
        Thread.sleep(1000)
        firstNameInput.perform(clearText())
        firstNameInput.perform(closeSoftKeyboard())
        changeNameButton.perform(click())
        submitButton.perform(click())

        onView(withText("The required fields are missing some information")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        changeNameButton.perform(click())
        Thread.sleep(1000)
        firstNameInput.perform(typeText("First"))
        lastNameInput.perform(clearText())
        lastNameInput.perform(closeSoftKeyboard())
        changeNameButton.perform(click())
        submitButton.perform(click())

        onView(withText("The required fields are missing some information")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        changeNameButton.perform(click())
        Thread.sleep(1000)
        lastNameInput.perform(typeText("Last"))
        lastNameInput.perform(closeSoftKeyboard())

        submitButton.perform(click())

        Thread.sleep(5000)

        assertTrue(updateInformationTestRule.activity.isFinishing)
    }

    @Test
    fun submitWithAddressTest() {
        changeAddressButton.perform(click())
        Thread.sleep(1000)
        streetAddressInput.perform(clearText())
        streetAddressInput.perform(closeSoftKeyboard())
        changeAddressButton.perform(click())
        submitButton.perform(click())

        onView(withText("The required fields are missing some information")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        changeAddressButton.perform(click())
        Thread.sleep(1000)
        streetAddressInput.perform(typeText("9999 Nine Avenue"))
        cityAddressInput.perform(clearText())
        cityAddressInput.perform(closeSoftKeyboard())
        changeAddressButton.perform(click())
        submitButton.perform(click())

        onView(withText("The required fields are missing some information")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        changeAddressButton.perform(click())
        Thread.sleep(1000)
        cityAddressInput.perform(typeText("Eight"))
        stateAddressInput.perform(clearText())
        stateAddressInput.perform(closeSoftKeyboard())
        changeAddressButton.perform(click())
        submitButton.perform(click())

        onView(withText("The required fields are missing some information")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        changeAddressButton.perform(click())
        Thread.sleep(1000)
        stateAddressInput.perform(typeText("RT"))
        zipCodeAddressInput.perform(clearText())
        zipCodeAddressInput.perform(closeSoftKeyboard())
        changeAddressButton.perform(click())
        submitButton.perform(click())

        onView(withText("The required fields are missing some information")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        changeAddressButton.perform(click())
        Thread.sleep(1000)
        zipCodeAddressInput.perform(typeText("99999"))
        zipCodeAddressInput.perform(closeSoftKeyboard())
        changeAddressButton.perform(click())

        submitButton.perform(click())

        Thread.sleep(5000)

        assertTrue(updateInformationTestRule.activity.isFinishing)
    }

    @Test
    fun submitWithPhoneNumberTest() {
        changePhoneNumberButton.perform(click())
        Thread.sleep(1000)
        phoneNumberInput.perform(clearText())
        phoneNumberInput.perform(closeSoftKeyboard())
        changePhoneNumberButton.perform(click())
        submitButton.perform(click())

        onView(withText("The required fields are missing some information")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        changePhoneNumberButton.perform(click())
        Thread.sleep(1000)
        phoneNumberInput.perform(typeText("3333333333"))
        phoneNumberInput.perform(closeSoftKeyboard())
        changePhoneNumberButton.perform(click())

        submitButton.perform(click())

        Thread.sleep(5000)

        assertTrue(updateInformationTestRule.activity.isFinishing)
    }
}