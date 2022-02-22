package com.example.photodiary.classes

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.photodiary.ui.PhotoDescriptionCreateActivity
import java.io.File

class PDCamera(private val activity: ComponentActivity): ActivityResultCallback<Boolean> {

    private var imageFile = File(activity.filesDir, "default_image.jpg")
    private val activityLauncher = activity.registerForActivityResult(ActivityResultContracts.TakePicture(), this)

    fun open() {
        imageFile = File(activity.filesDir, "test2.jpg")

        val fileUri = FileProvider.getUriForFile(
            activity,
            "com.example.photodiary.provider",
            imageFile
        )

        activityLauncher.launch(fileUri)
    }

    override fun onActivityResult(result: Boolean?) {
        val descriptionCreateIntent = Intent(activity, PhotoDescriptionCreateActivity::class.java)
        descriptionCreateIntent.putExtra("filePath", imageFile.path)
        descriptionCreateIntent.putExtra("fileUri", imageFile.toURI().toString())
        activity.startActivity(descriptionCreateIntent)
    }

}