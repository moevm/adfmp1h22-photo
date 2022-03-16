package com.example.photodiary

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.photodiary.classes.PDDB
import com.example.photodiary.classes.PhotoInfo
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DBTest {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val db = PDDB(context)

    @Test
    fun addAndDeletePhoto() {
        val beforeAddTotalCount = db.totalCount()
        val photoInfo = PhotoInfo()
        val id = db.addPhoto(photoInfo)
        val afterAddTotalCount = db.totalCount()
        db.deletePhotoById(id.toInt())
        val afterDeleteTotalCount = db.totalCount()

        assert(beforeAddTotalCount+1 == afterAddTotalCount)
        assert(beforeAddTotalCount == afterDeleteTotalCount)
    }

}