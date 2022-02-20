package com.example.photodiary.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.photodiary.R
import com.example.photodiary.classes.RemoveDialog
import com.example.photodiary.databinding.GalleryBinding
import java.lang.reflect.Array
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
        val month = arguments?.getInt("month")?.let { months[it] }
        val year = arguments?.getInt("year")
        dateText.text = "$day $month $year"

        _binding = GalleryBinding.inflate(inflater, container, false)

        val img1: ImageView = view.findViewById(R.id.imageView1)
        val img2: ImageView = view.findViewById(R.id.imageView2)
        val img3: ImageView = view.findViewById(R.id.imageView3)
        val img4: ImageView = view.findViewById(R.id.imageView4)

        val listener = View.OnLongClickListener{
            val removeDialog = RemoveDialog()
            val manager = parentFragmentManager
            removeDialog.show(manager, "remove")
            true
        }

        img1.setOnLongClickListener(listener)
        img2.setOnLongClickListener(listener)
        img3.setOnLongClickListener(listener)
        img4.setOnLongClickListener(listener)

        return view


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

