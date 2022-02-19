package com.example.photodiary.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.photodiary.R
import com.example.photodiary.databinding.CalendarBinding
import com.applandeo.materialcalendarview.CalendarView
import java.util.*


class CalendarFragment : Fragment() {

    private var _binding: CalendarBinding? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {


        _binding = CalendarBinding.inflate(inflater, container, false)

        val view: View = inflater.inflate(R.layout.calendar, container,
            false)

        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
        calendarView.setOnDayClickListener{eventDay ->
            val fragment = GalleryFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(container!!.id, fragment)
            transaction.commit()
        }

        val daysInARow: TextView = view.findViewById(R.id.daysInARow)
        val days = 7
        daysInARow.text = "Вы делаете записи $days подряд"


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

