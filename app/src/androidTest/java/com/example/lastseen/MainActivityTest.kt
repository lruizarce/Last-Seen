package com.example.lastseen

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule

import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    val mainActivityTestRule : ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    val signInButton = onView(withId(R.id.btSignIn))
    val usernameInput = onView(withId(R.id.etUsername))
    val passwordInput = onView(withId(R.id.etPass))
    val signUpLink = onView(withId(R.id.tvSignUp))

    @Test
    fun signInNoUsernameNoPasswordTest() {
        signInButton.perform(click())

        onView(withText("USERNAME AND PASSWORD CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }

    @Test
    fun signInNoUsernameTest() {
        passwordInput.perform(typeText("ExamplePassword"))
        closeSoftKeyboard()

        signInButton.perform(click())

        onView(withText("USERNAME AND PASSWORD CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }

    @Test
    fun signInNoPasswordTest() {
        usernameInput.perform(typeText("ExampleName"))
        closeSoftKeyboard()

        signInButton.perform(click())

        onView(withText("USERNAME AND PASSWORD CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }

    @Test
    fun signInSuccessTest() {
        usernameInput.perform(typeText("ExampleName"))
        closeSoftKeyboard()
        passwordInput.perform(typeText("ExamplePassword"))
        closeSoftKeyboard()

        signInButton.perform(click())

    }

    @Test
    fun signUpLinkTest() {
        Intents.init()
        signUpLink.perform(click())

        intended(hasComponent(CreateAccount::class.java.name))
        Intents.release()
    }
}