package com.example.photodiary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.photodiary.R
import com.example.photodiary.databinding.GalleryBinding
import java.util.*


class GalleryFragment : Fragment() {

    private var _binding: GalleryBinding? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {


        _binding = GalleryBinding.inflate(inflater, container, false)

        val view: View = inflater.inflate(R.layout.gallery, container,
            false)


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

