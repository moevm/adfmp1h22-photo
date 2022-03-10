package com.example.photodiary.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photodiary.R
import com.example.photodiary.databinding.CalendarBinding
import com.applandeo.materialcalendarview.CalendarView
import com.example.photodiary.classes.PDDB
import com.example.photodiary.classes.PhotoInfo
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.temporal.ChronoUnit
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

        val db = context?.let { PDDB(it) }
        var dates = db?.getAllDates()
        if (dates == null)
            dates = arrayListOf()

        val daysInARow: TextView = view.findViewById(R.id.date)
        val days = getCountDays(dates)
        daysInARow.text = "Вы оставляете записи $days день подряд"

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



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun getCountDays(dates: List<Date>): Int{
        var count = 0
        var day = Instant.now()
        Log.d("TAG", Date.from(day).toString())
        while (isDateInList(dates, Date.from(day))){
            Log.d("TAG", Date.from(day).toString())
            count++
            day = day.minus(1, ChronoUnit.DAYS)
        }
        return count
    }

    fun isDateInList(list: List<Date>, date: Date): Boolean{
        list.forEach{
            if(isDateEquals(it, date)){
                return true
            }
        }
        return false
    }

    fun isDateEquals(date1: Date, date2: Date): Boolean{
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date1WithoutTime = formatter.format(date1)
        val date2WithoutTime = formatter.format(date2)
        return date1WithoutTime.compareTo(date2WithoutTime) == 0
    }

}

