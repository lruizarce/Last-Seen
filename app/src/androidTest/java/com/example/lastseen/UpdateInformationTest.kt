package com.example.lastseen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class UpdateInformationTest {
    @Rule
    @JvmField
    val updateInformationTestRule : ActivityTestRule<UpdateInformation> = ActivityTestRule(UpdateInformation::class.java)

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

    private val changeEmergencyContact1Button = onView(withId(R.id.change_emergency_contact_1_button))
    private val emergencyContact1FirstNameInput = onView(withId(R.id.emergency_contact_1_first_name_input))
    private val emergencyContact1LastNameInput = onView(withId(R.id.emergency_contact_1_last_name_input))
    private val emergencyContact1PhoneNumberInput = onView(withId(R.id.emergency_contact_1_phone_number_input))
    private val emergencyContact1 = onView(withId(R.id.emergency_contact_1))

    private val changeEmergencyContact2Button = onView(withId(R.id.change_emergency_contact_2_button))
    private val emergencyContact2FirstNameInput = onView(withId(R.id.emergency_contact_2_first_name_input))
    private val emergencyContact2LastNameInput = onView(withId(R.id.emergency_contact_2_last_name_input))
    private val emergencyContact2PhoneNumberInput = onView(withId(R.id.emergency_contact_2_phone_number_input))
    private val emergencyContact2 = onView(withId(R.id.emergency_contact_2))

    private val submitButton = onView(withId(R.id.update_information_submit_button))

    @Test
    fun updateNameTest() {
        changeNameButton.perform(click())
        firstNameInput.perform(clearText())
        firstNameInput.perform(typeText("First"))
        lastNameInput.perform(clearText())
        lastNameInput.perform(typeText("Last"))
        changeNameButton.perform(click())

        name.check(matches(withText("First Last")))
    }

    @Test
    fun updateAddressTest() {
        changeAddressButton.perform(click())
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
    }

    @Test
    fun updatePhoneNumberTest() {
        changePhoneNumberButton.perform(click())
        phoneNumberInput.perform(clearText())
        phoneNumberInput.perform(typeText("9999999999"))
        changePhoneNumberButton.perform(click())

        phoneNumber.check(matches(withText("9999999999")))
    }

    @Test
    fun updateEmergencyContact1Test() {
        changeEmergencyContact1Button.perform(click())
        emergencyContact1FirstNameInput.perform(clearText())
        emergencyContact1FirstNameInput.perform(typeText("First"))
        emergencyContact1LastNameInput.perform(clearText())
        emergencyContact1LastNameInput.perform(typeText("Last"))
        emergencyContact1LastNameInput.perform(closeSoftKeyboard())
        emergencyContact1PhoneNumberInput.perform(clearText())
        emergencyContact1PhoneNumberInput.perform(typeText("8888888888"))
        changeEmergencyContact1Button.perform(click())

        emergencyContact1.check(matches(withText("First Last\n8888888888")))
    }

    @Test
    fun updateEmergencyContact2Test() {
        changeEmergencyContact2Button.perform(click())
        emergencyContact2FirstNameInput.perform(clearText())
        emergencyContact2FirstNameInput.perform(typeText("First"))
        emergencyContact2LastNameInput.perform(clearText())
        emergencyContact2LastNameInput.perform(typeText("Last"))
        emergencyContact2LastNameInput.perform(closeSoftKeyboard())
        emergencyContact2PhoneNumberInput.perform(clearText())
        emergencyContact2PhoneNumberInput.perform(typeText("8888888888"))
        changeEmergencyContact2Button.perform(click())

        emergencyContact2.check(matches(withText("First Last\n8888888888")))
    }

    @Test
    fun submitWithNameTest() {
        changeNameButton.perform(click())
        firstNameInput.perform(clearText())
        lastNameInput.perform(clearText())
        lastNameInput.perform(closeSoftKeyboard())
        changeNameButton.perform(click())
        submitButton.perform(click())

        onView(withText("The required fields are missing some information")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        changeNameButton.perform(click())
        firstNameInput.perform(typeText("First"))
        lastNameInput.perform(typeText("Last"))
        lastNameInput.perform(closeSoftKeyboard())
        submitButton.perform(click())

        assertTrue(updateInformationTestRule.activity.isFinishing)
    }

    @Test
    fun submitWithAddressTest() {
        changeAddressButton.perform(click())
        streetAddressInput.perform(clearText())
        streetAddressInput.perform(closeSoftKeyboard())
        changeAddressButton.perform(click())
        submitButton.perform(click())

        onView(withText("The required fields are missing some information")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        changeAddressButton.perform(click())
        streetAddressInput.perform(typeText("9999 Nine Avenue"))
        streetAddressInput.perform(closeSoftKeyboard())
        changeAddressButton.perform(click())
        submitButton.perform(click())

        assertTrue(updateInformationTestRule.activity.isFinishing)
    }

    @Test
    fun submitWithPhoneNumberTest() {
        changePhoneNumberButton.perform(click())
        phoneNumberInput.perform(clearText())
        phoneNumberInput.perform(closeSoftKeyboard())
        changePhoneNumberButton.perform(click())
        submitButton.perform(click())

        onView(withText("The required fields are missing some information")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        changePhoneNumberButton.perform(click())
        phoneNumberInput.perform(typeText("3333333333"))
        phoneNumberInput.perform(closeSoftKeyboard())
        changePhoneNumberButton.perform(click())
        submitButton.perform(click())

        assertTrue(updateInformationTestRule.activity.isFinishing)
    }
}