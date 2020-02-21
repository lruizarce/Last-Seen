package com.example.lastseen

import android.os.Handler
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.GoogleAuthProvider
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    val mainActivityTestRule : ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private val signUpLink = onView(withId(R.id.login_sign_up_link))
    private val googleSignInButton = onView(withId(R.id.google_sign_in_button))

    @Before
    fun initIntent() {
        Intents.init()
    }

    @After
    fun releaseIntent() {
        Intents.release()
    }

    @Test
    fun googleSignInSuccessfulTest() {
        googleSignInButton.perform(click())

        Thread.sleep(3000)

        assertTrue(mainActivityTestRule.activity.isFinishing)
        intended(hasComponent(Itinerary::class.java.name))
    }

    @Test
    fun signUpLinkTest() {
        signUpLink.perform(click())

        intended(hasComponent(CreateAccount::class.java.name))
    }
}