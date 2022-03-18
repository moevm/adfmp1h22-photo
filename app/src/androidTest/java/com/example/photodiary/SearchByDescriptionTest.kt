package com.example.photodiary

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.photodiary.classes.PDDB
import com.example.photodiary.classes.PhotoInfo
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern


@RunWith(AndroidJUnit4::class)
class SearchByDescriptionTest {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val db = PDDB(context)

    @Test
    fun searchByDescription() {
        val photoInfo1 = PhotoInfo("описание")
        val photoInfo2 = PhotoInfo("писатель")
        val photoInfo3 = PhotoInfo("письмо")
        val queryString = "описание"
        db.addPhoto(photoInfo1)
        db.addPhoto(photoInfo2)
        db.addPhoto(photoInfo3)
        val queryResult = db.getByDescription(queryString)
        assert(queryResult.isNotEmpty())
        queryResult.forEach {
            assert(Pattern.matches(".*описание.*", it.description))
        }
    }

}