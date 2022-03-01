package com.example.photodiary.classes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.photodiary.R
import com.example.photodiary.ui.GalleryFragment
import com.example.photodiary.ui.PhotoDescriptionCreateActivity
import java.io.File
import java.nio.file.Files.createFile
import java.text.SimpleDateFormat
import java.util.*

class PDCamera(private val activity: AppCompatActivity): ActivityResultCallback<Boolean> {

    private var photoInfo = PhotoInfo()
    private var imageFile = File(activity.filesDir, photoInfo.fileName)
    private val activityLauncher = activity.registerForActivityResult(ActivityResultContracts.TakePicture(), this)
    private val descriptionActivityLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val description = it.data?.getStringExtra("description")

            if (description != null) {

                photoInfo.description = description

                val db = PDDB(activity)
                db.addPhoto(photoInfo)

                val bundle = Bundle()

                val date = Calendar.getInstance()
                bundle.putInt("day", date.get(Calendar.DATE))
                bundle.putInt("month", date.get(Calendar.MONTH))
                bundle.putInt("year", date.get(Calendar.YEAR))

                val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)
                navController.navigate(R.id.navigation_gallery, bundle)
            }

        }
    }

    fun open() {

        val photoInfo = PhotoInfo()

        val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        imageFile = File(
            storageDir /* directory */,
            photoInfo.fileName, /* prefix */
        )

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