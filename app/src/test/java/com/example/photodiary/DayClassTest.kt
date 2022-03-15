package com.example.photodiary

import com.example.photodiary.classes.Day
import org.junit.Assert
import org.junit.Test

class DayClassTest {

    @Test
    fun stringDay() {
        val months = arrayOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
        val day = Day(11, 2, 2022)
        val stringDay = day.dayOfMonth.toString() + " " + months[day.month] + " " + day.year
        Assert.assertEquals("11 марта 2022", stringDay)
    }
}