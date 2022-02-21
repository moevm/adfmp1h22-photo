package com.example.photodiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.photodiary.classes.EditDialog
import com.example.photodiary.classes.RemoveDialog

class Photo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_photo)

        val textForEditing = "Текст к фотозаписи"

        val deleteButton: ImageButton = findViewById(R.id.delete)
        deleteButton.setOnClickListener{
            val removeDialog = RemoveDialog()
            val manager = supportFragmentManager
            removeDialog.show(manager, "remove")
            true
        }

        val editButton: ImageButton = findViewById(R.id.edit)
        editButton.setOnClickListener{
            val editDialog = EditDialog(textForEditing)
            val manager = supportFragmentManager
            editDialog.show(manager, "edit")
        }
    }
}