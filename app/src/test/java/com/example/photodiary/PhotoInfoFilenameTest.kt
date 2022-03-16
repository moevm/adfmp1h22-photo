package com.example.photodiary

import com.example.photodiary.classes.PhotoInfo
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class PhotoInfoFilenameTest {

    @Test
    fun constructorTest() {
        val photoInfo = PhotoInfo("description")
        Assert.assertEquals("description", photoInfo.description)
    }
}