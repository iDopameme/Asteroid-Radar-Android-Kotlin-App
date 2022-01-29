package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.getCurrentDate
import com.udacity.asteroidradar.database.AsteroidDao

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

    override suspend fun refreshAsteroids() {
        super.refreshAsteroids()
    }

    override fun getSavedAsteroids(): List<Asteroid> {
        return allAsteroids
    }

    override fun getTodaysAsteroid(): String {
        return todayAsteroids
    }

    override suspend fun saveAsteroid(asteroid: Asteroid) {
        asteroidDao.insert(asteroid)
    }

    override suspend fun deleteAsteroids() {
        asteroidDao.clearAsteroids()
    }

}