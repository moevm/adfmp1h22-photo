package com.example.photodiary.ui

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photodiary.databinding.ActivityPhotoDescriptionCreateBinding

class PhotoDescriptionCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoDescriptionCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityPhotoDescriptionCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uriString = intent.getStringExtra("fileUri")
//        val path = intent.getStringExtra("filePath")
        if (uriString == null) {
            Toast.makeText(this, "Error in transfer URI", Toast.LENGTH_LONG).show()
        } else {
//            binding.root.background = Drawable.createFromPath(path)
            binding.descriptionAddImageView.setImageURI(Uri.parse(uriString))
        }

        binding.doneButton.setOnClickListener {

            val resultIntent = Intent()
            resultIntent.putExtra("description", binding.descriptionTextEdit.text.toString())
            setResult(RESULT_OK, resultIntent)
            finish()
        }

    }
}