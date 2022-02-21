package com.example.photodiary.classes

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.photodiary.R

class EditDialog(textForEditing: String) : DialogFragment() {

    private val editingText = textForEditing

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activ = activity?.let {
            val view = layoutInflater.inflate(R.layout.edit_dialog, null)
            view.findViewById<EditText>(R.id.textField).setText(editingText)
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Изменение записи")
                .setNegativeButton("Отмена") { dialog, id ->

                }
                .setPositiveButton("Применить") { dialog, id ->
                    Toast.makeText(activity, "Изменено",
                        Toast.LENGTH_LONG).show()
                }
                .setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        return activ
    }
}