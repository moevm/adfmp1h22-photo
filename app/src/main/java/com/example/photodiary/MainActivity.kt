package com.example.photodiary

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.photodiary.classes.PDCamera
import com.example.photodiary.classes.PDDB
import com.example.photodiary.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)


        navView.setOnItemReselectedListener {
            navController.navigateUp()
        }

        val camera = PDCamera(this)
        navView.menu.getItem(1).setOnMenuItemClickListener {
            camera.open()
            false
        }

        val move = intent.getStringExtra("move")
        if (move != null){
            if (move == "gallery"){
                val day = intent.getIntExtra("day", 0)
                val month = intent.getIntExtra("month", 0)
                val year = intent.getIntExtra("year", 0)
                if (checkMoveToGallery(day, month, year)){
                    val bundle = Bundle()
                    bundle.putInt("day", day)
                    bundle.putInt("month", month)
                    bundle.putInt("year", year)

                    navController.navigate(R.id.navigation_gallery, bundle)
                }
            }

            else if (move == "search"){
                navController.navigate(R.id.navigation_search)
            }
        }


        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_statistic, R.id.navigation_calendar, R.id.navigation_search))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun checkMoveToGallery(day:Int, month:Int, year:Int): Boolean{
       return day in 1..31 && month in 0..11 && year > 0
    }

}