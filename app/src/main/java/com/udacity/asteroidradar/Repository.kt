package com.udacity.asteroidradar

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.database.AsteroidDao

class Repository : AsteroidRepository {

    private val asteroidDao: AsteroidDao by lazy {
        AsteroidApplication.database.asteroidDao()
    }

    private val allAsteroids by lazy {
        asteroidDao.getAllAsteroids()
    }

    override fun getSavedAsteroids(): LiveData<List<Asteroid>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAsteroid(asteroid: Asteroid) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAsteroids() {
        TODO("Not yet implemented")
    }

}