package com.udacity.asteroidradar.repository

import android.app.Application
import androidx.room.Room
import com.udacity.asteroidradar.database.AsteroidDatabase

class AsteroidApplication : Application() {

    private val DB_NAME = "asteroid_database"

    companion object {
        lateinit var database: AsteroidDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AsteroidDatabase::class.java, DB_NAME)
            .build()
    }
}