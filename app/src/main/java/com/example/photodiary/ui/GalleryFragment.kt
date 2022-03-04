package com.example.photodiary.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.photodiary.Photo
import com.example.photodiary.R
import com.example.photodiary.classes.PDDB
import com.example.photodiary.classes.PhotoInfo
import com.example.photodiary.classes.RemoveDialog
import com.example.photodiary.databinding.GalleryBinding
import java.util.*


class GalleryFragment : Fragment() {

    private var _binding: GalleryBinding? = null

    private val months = arrayOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val photos = PDDB(this.requireContext()).getByDate(Calendar.getInstance().time)

        val view: View = inflater.inflate(R.layout.gallery, container,
            false)

        val dateText: TextView = view.findViewById(R.id.date)
        val day = arguments?.getInt("day")
        val month = arguments?.getInt("month")
        val monthString = months[month!!]
        val year = arguments?.getInt("year")
        dateText.text = "$day $monthString $year"

        _binding = GalleryBinding.inflate(inflater, container, false)

        val db = context?.let { PDDB(it) }
        val selectedDay = Calendar.getInstance()
        if (year != null && day !=null) {
            selectedDay.set(year, month, day)
        }
        val list:List<PhotoInfo> = db?.getByDate(Date(selectedDay.timeInMillis))!!
        val table:TableLayout = view.findViewById(R.id.table)
        var tableRow: TableRow? = null
        for (i in list.indices) {
            if (i%2 == 0) {
                tableRow = TableRow(context)
                tableRow.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                val image = createImage(list[i])
                tableRow.addView(image)
            }

            else {
                val image = createImage(list[i])
                tableRow?.addView(image)
                table.addView(tableRow)
            }
        }
        if (list.size % 2 == 1){
            table.addView(tableRow)
        }

        return view

    }

    fun createImage(photoInfo: PhotoInfo): ImageView {
        val image = ImageView(context);
        image.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        val factor = context?.resources?.displayMetrics?.density!!
        val layoutParams = TableRow.LayoutParams((125*factor).toInt(), (125*factor).toInt())
        layoutParams.setMargins((25*factor).toInt(), (20*factor).toInt(), 0, 0)
        image.layoutParams = layoutParams
        image.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().path + "/Android/data/com.example.photodiary/files/Pictures/" + photoInfo.fileName))
        image.setOnClickListener{
            val intent = Intent(context, Photo::class.java)
            startActivity(intent)
        }
        image.setOnLongClickListener{
            val removeDialog = RemoveDialog()
            val manager = parentFragmentManager
            removeDialog.show(manager, "remove")
            true
        }
        return image
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

