package com.example.photodiary.classes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import java.text.SimpleDateFormat
import java.util.*


const val DATABASE_NAME = "PHOTO_DIARY"
const val TABLE_NAME = "PHOTO_DIARY"
const val ID_COLUMN = "_ID"
const val FILENAME_COLUMN = "FILENAME"
const val DESCRIPTION_COLUMN = "DESCRIPTION"
const val DATE_COLUMN = "DATE"
const val TIME_COLUMN = "TIME"

class PDOpenHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("create table $TABLE_NAME" +
                "($ID_COLUMN integer primary key autoincrement," +
                "$FILENAME_COLUMN text," +
                "$DESCRIPTION_COLUMN text," +
                "$DATE_COLUMN text," +
                "$TIME_COLUMN text)");

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $TABLE_NAME");
        onCreate(db);
    }
}

class PDDB(context: Context) {

    private val db: SQLiteDatabase
    private val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    private val timeFormat = SimpleDateFormat("hh:mm:ss")
    private val dateTimeFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")

    init {
        val dbOpenHelper = PDOpenHelper(context)
        db = dbOpenHelper.writableDatabase
    }

    fun addPhoto(photoInfo: PhotoInfo) {

        val newValues = ContentValues()
        newValues.put(FILENAME_COLUMN, photoInfo.fileName)
        newValues.put(DESCRIPTION_COLUMN, photoInfo.description)
        newValues.put(DATE_COLUMN, dateFormat.format(photoInfo.date))
        newValues.put(TIME_COLUMN, timeFormat.format(photoInfo.date))

        db.insert(TABLE_NAME, null, newValues)

    }

    fun getByDate(queryDate: Date): List<PhotoInfo> {

        val cursor = db.query(TABLE_NAME,
            arrayOf(ID_COLUMN, FILENAME_COLUMN, DESCRIPTION_COLUMN, DATE_COLUMN, TIME_COLUMN),
            "$DATE_COLUMN = ?",
            arrayOf(dateFormat.format(queryDate)),
            null, null, null
        )

        val idIndex = cursor.getColumnIndex(ID_COLUMN)
        val fileNameIndex = cursor.getColumnIndex(FILENAME_COLUMN)
        val descriptionIndex = cursor.getColumnIndex(DESCRIPTION_COLUMN)
        val dateIndex = cursor.getColumnIndex(DATE_COLUMN)
        val timeIndex = cursor.getColumnIndex(TIME_COLUMN)

        val result: MutableList<PhotoInfo> = mutableListOf()

        while(cursor.moveToNext()) {

            val id = cursor.getInt(idIndex)
            val fileName = cursor.getString(fileNameIndex)
            val description = cursor.getString(descriptionIndex)
            val date = cursor.getString(dateIndex)
            val time = cursor.getString(timeIndex)

            val dateTime = dateTimeFormat.parse("$date $time")

            if (dateTime != null) {
                result.add(PhotoInfo(id, dateTime, fileName, description))
            }

        }

        cursor.close()
        return result
    }


}