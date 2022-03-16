package com.example.photodiary

import com.example.photodiary.classes.PhotoInfo
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class PhotoInfoConstructorTest {

    @Test
    fun constructorTest() {
        val photoInfo = PhotoInfo()
        val filename = "${SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date())}.jpg"
        Assert.assertEquals(filename, photoInfo.fileName)
    }
}