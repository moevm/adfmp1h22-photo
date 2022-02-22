package com.example.photodiary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.photodiary.R
import com.example.photodiary.databinding.StatisticBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class StatisticFragment : Fragment() {

    private var _binding: StatisticBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {


        _binding = StatisticBinding.inflate(inflater, container, false)
        val view: View = inflater.inflate(R.layout.statistic, container, false)
        val root = binding.root

        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        for (i in 1..100) {
            entries.add(BarEntry(i.toFloat(), i*i.toFloat()))
            labels.add("Label $i")
        }

        val barDataSet = BarDataSet(entries, "Cells")
        val data = BarData(barDataSet)
        binding.chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chart.data = data

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}