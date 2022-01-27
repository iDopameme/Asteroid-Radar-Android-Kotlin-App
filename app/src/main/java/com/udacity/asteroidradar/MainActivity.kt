package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.asteroidradar.main.MainViewModel
import com.udacity.asteroidradar.repository.Repository

class MainActivity : AppCompatActivity() {


    // Standard onCreate function for MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
