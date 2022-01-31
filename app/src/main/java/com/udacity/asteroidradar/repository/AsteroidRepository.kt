package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.RetrofitInstance
import com.udacity.asteroidradar.api.getCurrentDate
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

interface AsteroidRepository {

    fun getAllAsteroid(): List<Asteroid>

    fun getTodaysAsteroid(): List<Asteroid>

    fun getWeekAsteroid(): List<Asteroid>

    suspend fun saveAsteroid(asteroid: Asteroid)

    suspend fun deleteAsteroids()

    suspend fun refreshAsteroids()
}