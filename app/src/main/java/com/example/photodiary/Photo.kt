package com.example.photodiary

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.photodiary.classes.EditDialog
import com.example.photodiary.classes.PDDB

class Photo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_photo)


        val db = PDDB(applicationContext)
        val imageId: Int = intent.getIntExtra("imageId", 0)
        val photoInfo = db.getPhotoInfoById(imageId)

        val imageView: ImageView = findViewById(R.id.image)
        imageView.setImageURI(
            Uri.parse(Environment.getExternalStorageDirectory().path + "/Android/data/com.example.photodiary/files/Pictures/" + photoInfo?.fileName))

        val descriptionView: TextView = findViewById(R.id.description)
        descriptionView.text = photoInfo?.description

        val deleteButton: ImageButton = findViewById(R.id.delete)
        deleteButton.setOnClickListener{
            val removeDialog = RemoveDialog(1)
            val manager = supportFragmentManager
            removeDialog.show(manager, "remove")
            true
        }

        val editButton: ImageButton = findViewById(R.id.edit)
        editButton.setOnClickListener{
            val editDialog = EditDialog(imageId, photoInfo?.description, descriptionView)
            val manager = supportFragmentManager
            editDialog.show(manager, "edit")
        }

        val shareButton: ImageButton = findViewById(R.id.share)
        shareButton.setOnClickListener{
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, "@mipmap/img")
                type = "image/*"
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }
    }
}