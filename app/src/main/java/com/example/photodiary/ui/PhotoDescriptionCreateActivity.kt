package com.example.photodiary.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photodiary.databinding.ActivityPhotoDescriptionCreateBinding


class PhotoDescriptionCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoDescriptionCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoDescriptionCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val path = intent.getStringExtra("filePath")
        if (path == null) {
            Toast.makeText(this, "Error in transfer path", Toast.LENGTH_LONG).show()
        } else {
            binding.root.background = Drawable.createFromPath(path)
        }

    }
}