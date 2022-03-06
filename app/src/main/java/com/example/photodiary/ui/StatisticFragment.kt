package com.example.photodiary.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.photodiary.R
import com.example.photodiary.classes.PDDB
import com.example.photodiary.databinding.StatisticBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.DateFormat.getDateInstance
import java.time.LocalDate

class StatisticFragment : Fragment() {

    private var _binding: StatisticBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = StatisticBinding.inflate(inflater, container, false)
        val root = binding.root

        val statistic = PDDB(requireContext()).getStatistic()
        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        for (i in statistic.indices) {
            entries.add(BarEntry(i.toFloat(), statistic[i].second.toFloat()))
            labels.add(getDateInstance().format(statistic[i].first))
        }

        val barDataSet = BarDataSet(entries, "Cells")
        val data = BarData(barDataSet)
        binding.chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chart.xAxis.labelRotationAngle = 90F
        binding.chart.axisLeft.axisMinimum = 0F

        binding.chart.data = data
        binding.chart.legend.isEnabled = false
        binding.chart.description.isEnabled = false
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}