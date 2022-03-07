package com.example.photodiary.classes

import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.photodiary.ui.GalleryFragment
import java.io.File

class RemoveDialog(id: Int?, imageFile: File?, galleryFragment: GalleryFragment) : DialogFragment() {

    private val imageId = id
    private val galleryFragment = galleryFragment
    private val imageFile = imageFile

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val db = context?.let { PDDB(it) }
        val activ = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Вы действительно хотите удалить запись")
                .setPositiveButton("Нет") { dialog, id ->
                }
                .setNegativeButton("Да") { dialog, id ->
                    imageId?.let { it1 -> db?.deletePhotoById(it1) }
                    val detachTransactional = parentFragmentManager.beginTransaction()
                    val attachTransactional = parentFragmentManager.beginTransaction()
                    detachTransactional.detach(galleryFragment)
                    detachTransactional.commit()
                    attachTransactional.attach(galleryFragment)
                    attachTransactional.commit()
                    imageFile?.delete()
                    Toast.makeText(activity, "Запись удалена",
                        Toast.LENGTH_LONG).show()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        return activ
    }
}