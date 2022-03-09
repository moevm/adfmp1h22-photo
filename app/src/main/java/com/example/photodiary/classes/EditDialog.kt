package com.example.photodiary.classes

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.photodiary.R

class EditDialog(imageId: Int, description: String?, textView: TextView) : DialogFragment() {

    private val imageId = imageId
    private val description = description
    private val textView = textView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activ = activity?.let {
            val view = layoutInflater.inflate(R.layout.edit_dialog, null)
            val textField = view.findViewById<EditText>(R.id.textField)
            textField.setText(description)
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Изменение записи")
                .setNegativeButton("Отмена") { dialog, id ->
                }
                .setPositiveButton("Применить") { dialog, id ->
                    val db = context?.let { PDDB(it) }
                    db?.updatePhotoDescription(imageId, textField.text.toString())
                    textView.text = textField.text.toString()
                }
                .setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        return activ
    }
}