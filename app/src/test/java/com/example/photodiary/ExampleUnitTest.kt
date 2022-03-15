package com.example.photodiary

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.photodiary.classes.PDDB
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addPhoto() {
        val db = PDDB(Context())
    }
}