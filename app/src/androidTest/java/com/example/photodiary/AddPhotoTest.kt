package com.example.photodiary

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.truth.content.IntentSubject.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddPhotoTest {

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun openCamera() {
        Intents.init()

        // Build a result to return from the Camera app
        val icon = BitmapFactory.decodeResource(
            ApplicationProvider.getApplicationContext<Context>().resources,
            R.mipmap.ic_launcher
        )
        val resultData = Intent()
        resultData.putExtra("data", icon)
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);

        onView(withId(R.id.navigation_camera)).perform(click())
        val receivedIntent: Intent = Iterables.getFirst(Intents.getIntents(), Intent())
        assertThat(receivedIntent).hasAction(MediaStore.ACTION_IMAGE_CAPTURE)

        onView(withId(R.id.descriptionTextEdit)).perform(
            ViewActions.clearText(),
            ViewActions.typeText("Hello World!"),
            ViewActions.closeSoftKeyboard()
        ).check(ViewAssertions.matches(ViewMatchers.withText("Hello World!")))
    }

}