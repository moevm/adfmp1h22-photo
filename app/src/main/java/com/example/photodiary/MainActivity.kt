package com.example.photodiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.photodiary.classes.PDCamera
import com.example.photodiary.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navView.setOnItemReselectedListener {
            supportFragmentManager.popBackStack()
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val camera = PDCamera(this)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id.toString() == R.id.navigation_camera.toString()) {
                camera.open()
            }
        }

        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_statistic, R.id.navigation_camera, R.id.navigation_calendar, R.id.navigation_search))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}