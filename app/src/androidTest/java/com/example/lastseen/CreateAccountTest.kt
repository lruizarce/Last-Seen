package com.example.lastseen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

class CreateAccountTest {
    @Rule
    @JvmField
    val createAccountTestRule : ActivityTestRule<CreateAccount> = ActivityTestRule(CreateAccount::class.java)

    private val submit = onView(withId(R.id.create_account_submit_button))
    private val firstName = onView(withId(R.id.create_account_first_name_input))
    private val lastName = onView(withId(R.id.create_account_last_name_input))
    private val dateOfBirth = onView(withId(R.id.create_account_date_of_birth_input))
    private val streetAddress = onView(withId(R.id.create_account_street_address_input))
    private val city = onView(withId(R.id.create_account_city_input))
    private val state = onView(withId(R.id.create_account_state_input))
    private val zipCode = onView(withId(R.id.create_account_zip_code_input))
    private val phoneNumber = onView(withId(R.id.create_account_phone_number_input))
    private val macAddress = onView(withId(R.id.create_account_mac_address_input))
    private val emergencyContactFirstName = onView(withId(R.id.create_account_emergency_contact_first_name_input))
    private val emergencyContactLastName = onView(withId(R.id.create_account_emergency_contact_last_name_input))
    private val emergencyContactPhoneNumber = onView(withId(R.id.create_account_emergency_contact_phone_number_input))
    private val googleSignInButton = onView(withId(R.id.create_account_google_sign_in_button))

    @Test
    fun submitSuccessfulTest() {
        submit.check(matches(not(isEnabled())))

        firstName.perform(click())
        firstName.perform(typeText("Aaron"))
        firstName.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        lastName.perform(click())
        lastName.perform(typeText("Baron"))
        lastName.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        streetAddress.perform(click())
        streetAddress.perform(typeText("3333 Third Road"))
        streetAddress.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        city.perform(click())
        city.perform(typeText("Twenty"))
        city.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        state.perform(click())
        state.perform(typeText("RT"))
        state.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        zipCode.perform(click())
        zipCode.perform(typeText("3333 Third Road"))
        zipCode.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        phoneNumber.perform(click())
        phoneNumber.perform(typeText("4445559999"))
        phoneNumber.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        dateOfBirth.perform(click())
        dateOfBirth.perform(typeText("01/12/1890"))
        dateOfBirth.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        macAddress.perform(click())
        macAddress.perform(typeText("EE:FF:00:88"))
        macAddress.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        emergencyContactFirstName.perform(click())
        emergencyContactFirstName.perform(typeText("Anne"))
        emergencyContactFirstName.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        emergencyContactLastName.perform(click())
        emergencyContactLastName.perform(typeText("Pot"))
        emergencyContactLastName.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        emergencyContactPhoneNumber.perform(click())
        emergencyContactPhoneNumber.perform(typeText("5677651234"))
        emergencyContactPhoneNumber.perform(closeSoftKeyboard())

        submit.check(matches(not(isEnabled())))

        googleSignInButton.perform(click())

        Thread.sleep(3000)

        submit.check(matches(isEnabled()))

        submit.perform(click())

        Thread.sleep(3000)

        assertTrue(createAccountTestRule.activity.isFinishing)
    }

    @Test
    fun missingFields() {
        googleSignInButton.perform(click())

        Thread.sleep(3000)

        submit.check(matches(isEnabled()))
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
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        macAddress.perform(click())
        macAddress.perform(typeText("EE:FF:00:88"))
        macAddress.perform(closeSoftKeyboard())

        submit.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        emergencyContactFirstName.perform(click())
        emergencyContactFirstName.perform(typeText("Anne"))
        emergencyContactFirstName.perform(closeSoftKeyboard())

        submit.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        emergencyContactLastName.perform(click())
        emergencyContactLastName.perform(typeText("Pot"))
        emergencyContactLastName.perform(closeSoftKeyboard())

        submit.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

        emergencyContactPhoneNumber.perform(click())
        emergencyContactPhoneNumber.perform(typeText("5677651234"))
        emergencyContactPhoneNumber.perform(closeSoftKeyboard())

        submit.perform(click())

        Thread.sleep(3000)

        assertTrue(createAccountTestRule.activity.isFinishing)
    }
}