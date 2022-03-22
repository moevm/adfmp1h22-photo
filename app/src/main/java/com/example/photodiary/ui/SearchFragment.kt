package com.example.photodiary.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.photodiary.Photo
import com.example.photodiary.R
import com.example.photodiary.classes.PDDB
import com.example.photodiary.databinding.SearchBinding
import java.text.SimpleDateFormat
import java.util.*

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

        val factor = context?.resources?.displayMetrics?.density!!

        val linearLayout: LinearLayout = view.findViewById(R.id.linearLayout)
        searchByDescription(linearLayout, "", factor)

        val searchView: SearchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchByDescription(linearLayout, newText, factor)
                return true
            }
        })

        return view
    }

    fun createHorizontalLinearLayout(factor: Float, imageId: Int?): LinearLayout{
        val horizontalLinearLayout = LinearLayout(context)
        val horizontalLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            (85*factor).toInt()
        )
        horizontalLayoutParams.setMargins(0, (10*factor).toInt(), 0, (5*factor).toInt())
        horizontalLinearLayout.layoutParams = horizontalLayoutParams
        horizontalLinearLayout.orientation = LinearLayout.HORIZONTAL
        horizontalLinearLayout.setOnClickListener {
            val intent = Intent(context, Photo::class.java)
            intent.putExtra("imageId", imageId)
            startActivity(intent)
        }
        return horizontalLinearLayout
    }

    fun createImageView(factor: Float, fileName:String): ImageView {
        val image = ImageView(context);
        image.layoutParams = LinearLayout.LayoutParams(
            (104*factor).toInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        image.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().path + "/Android/data/com.example.photodiary/files/Pictures/" + fileName))
        return image
    }

    fun createImageDescriptionView(factor: Float, descriptionText: String): TextView{
        val description = TextView(context);
        description.layoutParams = LinearLayout.LayoutParams(
            (175*factor).toInt(),
            100*factor.toInt()
        )
        description.gravity = Gravity.CENTER_VERTICAL
        description.text = descriptionText
        return description
    }

    fun createImageDateView(factor: Float, dateStr: String): TextView{
        val date = TextView(context);
        date.layoutParams = LinearLayout.LayoutParams(
            (175*factor).toInt(),
            30*factor.toInt()
        )
        date.gravity = Gravity.RIGHT
        date.text = dateStr
        date.textSize = 12f
        return date
    }

    fun searchByDescription(linearLayout: LinearLayout, text: String, factor: Float){
        val db = context?.let { PDDB(it) }
        val photoInfos = db?.getByDescription(text)
        linearLayout.removeAllViews()
        photoInfos?.forEach{
            val horizontalLinearLayout = createHorizontalLinearLayout(factor, it.id)
            val image = createImageView(factor, it.fileName)


            val description = createImageDescriptionView(factor, it.description)
            val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
            val date = createImageDateView(factor, simpleDateFormat.format(it.date))
            val textLayout = LinearLayout(context)
            textLayout.orientation = LinearLayout.VERTICAL
            textLayout.addView(description)
            textLayout.addView(date)

            horizontalLinearLayout.addView(image)
            horizontalLinearLayout.addView(textLayout)
            linearLayout.addView(horizontalLinearLayout)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}