package com.udacity.asteroidradar

import androidx.lifecycle.LiveData

interface AsteroidRepository {

    fun getSavedAsteroids(): LiveData<List<Asteroid>>

    suspend fun saveAsteroid(asteroid: Asteroid)

    suspend fun deleteAsteroids()
}