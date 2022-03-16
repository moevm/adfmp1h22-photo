package com.example.photodiary.classes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


const val DATABASE_NAME = "PHOTO_DIARY"
const val TABLE_NAME = "PHOTO_DIARY"
const val ID_COLUMN = "_ID"
const val FILENAME_COLUMN = "FILENAME"
const val DESCRIPTION_COLUMN = "DESCRIPTION"
const val DATE_COLUMN = "DATE"
const val TIME_COLUMN = "TIME"

const val COUNT_COLUMN = "count($DATE_COLUMN)"
const val TOTAL_COUNT = "count(*)"

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

    fun addPhoto(photoInfo: PhotoInfo): Long {

        val newValues = ContentValues()
        newValues.put(FILENAME_COLUMN, photoInfo.fileName)
        newValues.put(DESCRIPTION_COLUMN, photoInfo.description)
        newValues.put(DATE_COLUMN, dateFormat.format(photoInfo.date))
        newValues.put(TIME_COLUMN, timeFormat.format(photoInfo.date))

        return db.insert(TABLE_NAME, null, newValues)

    }

    fun updatePhotoDescription(id: Int, description: String?){
        val updateValues = ContentValues()
        updateValues.put(DESCRIPTION_COLUMN, description)
        db.update(TABLE_NAME, updateValues, "$ID_COLUMN=$id", null)
    }

    fun deletePhotoById(id: Int){
        db.delete(TABLE_NAME, "$ID_COLUMN=$id", null)
    }

    fun getPhotoInfoById(imageId: Int): PhotoInfo?{
        val cursor = db.query(TABLE_NAME,
            arrayOf(ID_COLUMN, FILENAME_COLUMN, DESCRIPTION_COLUMN, DATE_COLUMN, TIME_COLUMN),
            "$ID_COLUMN=?",
            arrayOf(imageId.toString()), null, null, null
        )

        val idIndex = cursor.getColumnIndex(ID_COLUMN)
        val fileNameIndex = cursor.getColumnIndex(FILENAME_COLUMN)
        val descriptionIndex = cursor.getColumnIndex(DESCRIPTION_COLUMN)
        val dateIndex = cursor.getColumnIndex(DATE_COLUMN)
        val timeIndex = cursor.getColumnIndex(TIME_COLUMN)

        var photoInfo: PhotoInfo? = null
        while(cursor.moveToNext()) {

            val id = cursor.getInt(idIndex)
            val fileName = cursor.getString(fileNameIndex)
            val description = cursor.getString(descriptionIndex)
            val date = cursor.getString(dateIndex)
            val time = cursor.getString(timeIndex)

            val dateTime = dateTimeFormat.parse("$date $time")

            photoInfo = PhotoInfo(id, dateTime, fileName, description)
        }

        cursor.close()
        return photoInfo
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

    fun getByDescription(query: String): List<PhotoInfo> {

        val cursor = db.query(TABLE_NAME,
            arrayOf(ID_COLUMN, FILENAME_COLUMN, DESCRIPTION_COLUMN, DATE_COLUMN, TIME_COLUMN),
            "$DESCRIPTION_COLUMN LIKE ?",
            arrayOf("%$query%"),
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

    fun getAllDates(): List<Date> {

        val cursor = db.query(TABLE_NAME,
            arrayOf(ID_COLUMN, FILENAME_COLUMN, DESCRIPTION_COLUMN, DATE_COLUMN, TIME_COLUMN),
            null,
            null,
            null, null, null
        )

        val dateIndex = cursor.getColumnIndex(DATE_COLUMN)
        val timeIndex = cursor.getColumnIndex(TIME_COLUMN)

        val result: MutableList<Date> = mutableListOf()

        while(cursor.moveToNext()) {

            val date = cursor.getString(dateIndex)
            val time = cursor.getString(timeIndex)

            val dateTime = dateTimeFormat.parse("$date $time")

            if (dateTime != null) {
                result.add(dateTime)
            }

        }

        cursor.close()
        return result
    }

    fun getStatistic(): List<Pair<Date, Int>> {

        val cursor = db.query(TABLE_NAME,
            arrayOf(DATE_COLUMN, COUNT_COLUMN),
            null, arrayOf(),
            DATE_COLUMN,
            null, null)

        val dateIndex = cursor.getColumnIndex(DATE_COLUMN)
        val countIndex = cursor.getColumnIndex(COUNT_COLUMN)

        val result: MutableList<Pair<Date, Int>> = mutableListOf()

        while(cursor.moveToNext()) {
            val date = dateFormat.parse(cursor.getString(dateIndex))
            val count = cursor.getInt(countIndex)

            if (date != null) result.add(Pair(date, count));
        }

        cursor.close()
        return result
    }

    fun totalCount(): Int {
        val cursor = db.query(TABLE_NAME, arrayOf(TOTAL_COUNT),
            null, null, null, null, null)

        val totalCountIndex = cursor.getColumnIndex(TOTAL_COUNT)
        cursor.moveToNext()
        val result = cursor.getInt(totalCountIndex)

        cursor.close()

        return result
    }

}