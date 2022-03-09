package com.example.photodiary.classes

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.photodiary.MainActivity
import com.example.photodiary.ui.GalleryFragment
import java.io.File

class RemoveDialog(private val imageId: Int?,
                   private val imageFile: File?,
                   private val galleryFragment: GalleryFragment?,
                   private val moveToSearch: Boolean,
                   private val moveToGallery: Boolean,
                   private val dayToMove: Day?) : DialogFragment() {

    constructor(imageId: Int?, imageFile: File?, dayToMove: Day): this(imageId, imageFile, null, false, true, dayToMove)

    constructor(imageId: Int?, imageFile: File?, galleryFragment: GalleryFragment?): this(imageId, imageFile, galleryFragment, false, false, null)

    constructor(imageId: Int?, imageFile: File?) : this(imageId, imageFile, null, true, false, null)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val db = context?.let { PDDB(it) }
        val activ = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Вы действительно хотите удалить запись")
                .setPositiveButton("Нет") { dialog, id ->
                }
                .setNegativeButton("Да") { dialog, id ->
                    imageId?.let { it1 -> db?.deletePhotoById(it1) }
                    if (galleryFragment !== null) {
                        val detachTransactional = parentFragmentManager.beginTransaction()
                        val attachTransactional = parentFragmentManager.beginTransaction()
                        detachTransactional.detach(galleryFragment)
                        detachTransactional.commit()
                        attachTransactional.attach(galleryFragment)
                        attachTransactional.commit()
                        imageFile?.delete()
                    }
                    if (moveToGallery) {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("move", "gallery")
                        intent.putExtra("day", dayToMove?.dayOfMonth)
                        intent.putExtra("month", dayToMove?.month)
                        intent.putExtra("year", dayToMove?.year)
                        startActivity(intent)
                    }
                    if (moveToSearch){
                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("move", "search")
                        startActivity(intent)
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        return activ
    }
}