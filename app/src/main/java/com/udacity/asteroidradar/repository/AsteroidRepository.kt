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

    fun getSavedAsteroids(): List<Asteroid>

    fun getTodaysAsteroid(): String

    suspend fun saveAsteroid(asteroid: Asteroid)

    suspend fun deleteAsteroids()

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val queuedAsteroid = RetrofitInstance.api.requestNeoWs(getCurrentDate(), Constants.API_KEY)

            val asteroidJSON = JSONObject(queuedAsteroid)
            val asteroids = parseAsteroidsJsonResult(asteroidJSON)

            for (asteroid in asteroids) {
                saveAsteroid(asteroid)
            }
        }
    }
}