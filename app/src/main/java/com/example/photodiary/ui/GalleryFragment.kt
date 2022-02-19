package com.example.photodiary.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.photodiary.R
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



        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

