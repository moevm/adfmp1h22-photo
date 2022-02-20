package com.example.photodiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.photodiary.classes.RemoveDialog

class Photo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_photo)

        val deleteButton: ImageButton = findViewById(R.id.delete)
        deleteButton.setOnClickListener{
            val removeDialog = RemoveDialog()
            val manager = supportFragmentManager
            removeDialog.show(manager, "remove")
            true
        }
    }
}