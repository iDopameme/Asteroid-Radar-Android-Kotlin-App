package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Asteroid

interface AsteroidRepository {

    fun getSavedAsteroids(): LiveData<List<Asteroid>>

    suspend fun saveAsteroid(asteroid: Asteroid)

    suspend fun deleteAsteroids()
}