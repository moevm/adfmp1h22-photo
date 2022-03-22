package com.example.photodiary.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.photodiary.Photo
import com.example.photodiary.R
import com.example.photodiary.classes.Day
import com.example.photodiary.classes.PDDB
import com.example.photodiary.classes.PhotoInfo
import com.example.photodiary.classes.RemoveDialog
import com.example.photodiary.databinding.GalleryBinding
import java.io.File
import java.util.*


class GalleryFragment : Fragment() {

    private var _binding: GalleryBinding? = null

    private val months = arrayOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

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
        val photos:List<PhotoInfo> = db?.getByDate(Date(selectedDay.timeInMillis))!!
        val table:TableLayout = view.findViewById(R.id.table)
        var tableRow: TableRow? = null
        for (i in photos.indices) {
            if (i%2 == 0) {
                tableRow = TableRow(context)
                tableRow.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                val image = createImage(photos[i])
                tableRow.addView(image)
//                if (i == photos.size-1){
//                    table.addView(tableRow)
//                }
            }

            else {
                val image = createImage(photos[i])
                tableRow?.addView(image)
                table.addView(tableRow)
            }
        }
        if (photos.size % 2 == 1){
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
            intent.putExtra("imageId", photoInfo.id)
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = photoInfo?.date
            intent.putExtra("day", Day(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)))
            startActivity(intent)
        }
        image.setOnLongClickListener{
            Log.d("TAG", "Gallery: " + photoInfo.id.toString())
            val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File(storageDir, photoInfo.fileName)
            val removeDialog = RemoveDialog(photoInfo.id, imageFile,this)
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

