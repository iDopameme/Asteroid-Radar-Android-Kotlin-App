package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroid: Asteroid)

    @Query("DELETE FROM asteroid_radar")
    fun clearAsteroids()

    @Delete
    fun deleteAsteroid(asteroid: Asteroid)

    @Query("SELECT * FROM asteroid_radar ORDER BY close_approach_date ASC")
    fun getAllAsteroids(): List<Asteroid>

    @Query("SELECT close_approach_date FROM asteroid_radar WHERE close_approach_date = :today")
    fun getTodaysAsteroids(today: String): String
}