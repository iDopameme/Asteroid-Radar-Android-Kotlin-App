package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.RetrofitInstance
import com.udacity.asteroidradar.api.getCurrentDate
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Repository : AsteroidRepository {

    private val asteroidDao: AsteroidDao by lazy {
        AsteroidApplication.database.asteroidDao()
    }

    private val allAsteroids by lazy {
        asteroidDao.getAllAsteroids()
    }

    private val todayAsteroids by lazy {
        asteroidDao.getTodaysAsteroids(getCurrentDate())
    }

    private val weeklyAsteroids by lazy {
        asteroidDao.getWeekAsteroids(getCurrentDate())
    }

    override suspend fun refreshAsteroids() {
            withContext(Dispatchers.IO) {
                val queuedAsteroid = RetrofitInstance.api.requestNeoWs(getCurrentDate(), Constants.API_KEY)

                val asteroidJSON = JSONObject(queuedAsteroid)
                val asteroids = parseAsteroidsJsonResult(asteroidJSON)

                for (asteroid in asteroids) {
                    saveAsteroid(asteroid)
                }
            }
    }

    override fun getAllAsteroid(): List<Asteroid> {
        return allAsteroids
    }

    override fun getTodaysAsteroid(): List<Asteroid> {
        return todayAsteroids
    }

    override fun getWeekAsteroid(): List<Asteroid> {
        return weeklyAsteroids
    }

    override suspend fun saveAsteroid(asteroid: Asteroid) {
        asteroidDao.insert(asteroid)
    }

    override suspend fun deleteAsteroids() {
        asteroidDao.clearAsteroids()
    }

}