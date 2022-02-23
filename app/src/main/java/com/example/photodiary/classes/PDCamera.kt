package com.example.photodiary.classes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.photodiary.R
import com.example.photodiary.ui.GalleryFragment
import com.example.photodiary.ui.PhotoDescriptionCreateActivity
import java.io.File
import java.util.*

class PDCamera(private val activity: AppCompatActivity): ActivityResultCallback<Boolean> {

    private var imageFile = File(activity.filesDir, "default_image.jpg")
    private val activityLauncher = activity.registerForActivityResult(ActivityResultContracts.TakePicture(), this)
    private val descriptionActivityLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val description = it.data?.getStringExtra("description")

            val fragment = GalleryFragment()
            val bundle = Bundle()

            val date = Calendar.getInstance()
            bundle.putInt("day", date.get(Calendar.DATE))
            bundle.putInt("month", date.get(Calendar.MONTH))
            bundle.putInt("year", date.get(Calendar.YEAR))
            fragment.arguments = bundle

            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment_activity_main, fragment).addToBackStack("gallery")
            transaction.commit()
        }
    }

    fun open() {
        imageFile = File(activity.filesDir, "test.jpg")

        val fileUri = FileProvider.getUriForFile(
            activity,
            "com.example.photodiary.provider",
            imageFile
        )

        activityLauncher.launch(fileUri)
    }

    override fun onActivityResult(result: Boolean?) {
        if (result != null && result) {
            val descriptionCreateIntent =
                Intent(activity, PhotoDescriptionCreateActivity::class.java)
//        descriptionCreateIntent.putExtra("filePath", imageFile.path)
            descriptionCreateIntent.putExtra("fileUri", imageFile.toURI().toString())
            descriptionActivityLauncher.launch(descriptionCreateIntent)
        }
    }

}