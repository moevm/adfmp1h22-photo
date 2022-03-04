package com.example.photodiary.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photodiary.R
import com.example.photodiary.databinding.CalendarBinding
import com.applandeo.materialcalendarview.CalendarView
import com.example.photodiary.classes.PDDB
import com.example.photodiary.classes.PhotoInfo
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

            val date = eventDay.calendar
            val bundle = Bundle()
            bundle.putInt("day", date.get(Calendar.DATE))
            bundle.putInt("month", date.get(Calendar.MONTH))
            bundle.putInt("year", date.get(Calendar.YEAR))

            val navController = findNavController()
            navController.navigate(R.id.navigation_gallery, bundle)
        }

        val daysInARow: TextView = view.findViewById(R.id.date)
        val days = 7
        daysInARow.text = "Вы делаете записи $days подряд"


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

