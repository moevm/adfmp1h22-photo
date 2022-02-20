package com.example.photodiary.classes

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class RemoveDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activ = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Вы действительно хотите удалить запись")
                .setPositiveButton("Нет") { dialog, id ->
                    Toast.makeText(activity, "Не удалено",
                        Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Да") { dialog, id ->
                        Toast.makeText(activity, "Запись удалена",
                            Toast.LENGTH_LONG).show()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        return activ
    }
}