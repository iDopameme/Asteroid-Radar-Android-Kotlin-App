package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDao

class Repository : AsteroidRepository {

    private val asteroidDao: AsteroidDao by lazy {
        AsteroidApplication.database.asteroidDao()
    }

    private val allAsteroids by lazy {
        asteroidDao.getAllAsteroids()
    }

    override fun getSavedAsteroids(): LiveData<List<Asteroid>> {
        return allAsteroids
    }

    override suspend fun saveAsteroid(asteroid: Asteroid) {
        asteroidDao.insert(asteroid)
    }

    override suspend fun deleteAsteroids() {
        asteroidDao.clearAsteroids()
    }

}