package com.example.photodiary.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.photodiary.Photo
import com.example.photodiary.R
import com.example.photodiary.databinding.SearchBinding

class SearchFragment : Fragment() {

    private var _binding: SearchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = SearchBinding.inflate(inflater, container, false)


        val view: View = inflater.inflate(R.layout.search, container,
            false)

        val listener = View.OnClickListener{
            val intent:Intent = Intent(context, Photo::class.java)
            startActivity(intent)
        }


        val linear1:LinearLayout = view.findViewById(R.id.linear1)
        val linear2:LinearLayout = view.findViewById(R.id.linear2)
        val linear3:LinearLayout = view.findViewById(R.id.linear3)

        linear1.setOnClickListener(listener)
        linear2.setOnClickListener(listener)
        linear3.setOnClickListener(listener)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}