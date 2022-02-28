package com.example.photodiary.classes

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PhotoInfo(
    val id: Int? = null,
    val date: Date = Calendar.getInstance().time,
    val fileName: String = "${SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date)}.jpg",
    var description: String = ""
) {

    constructor(_description: String): this(description = _description) {}

}