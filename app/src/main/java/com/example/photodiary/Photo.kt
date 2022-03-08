package com.example.photodiary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.photodiary.classes.Day
import com.example.photodiary.classes.EditDialog
import com.example.photodiary.classes.PDDB
import com.example.photodiary.classes.RemoveDialog
import java.io.File


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

        val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, photoInfo?.fileName)

        val deleteButton: ImageButton = findViewById(R.id.delete)
        deleteButton.setOnClickListener{
            val day = intent.getSerializableExtra("day") as Day
            val removeDialog = RemoveDialog(photoInfo?.id, imageFile, day)
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
//            val shareIntent = Intent(Intent.ACTION_SEND)
//            shareIntent.type = "image/jpg"
//            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory().path + "/Android/data/com.example.photodiary/files/Pictures/" + photoInfo?.fileName))
//            startActivity(Intent.createChooser(shareIntent, "Share image using"))

            val builder = VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/jpg"
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile))
            Log.d("TAG", Uri.fromFile(imageFile).toString())
            startActivity(Intent.createChooser(shareIntent, "Share link using"))
        }
    }
}