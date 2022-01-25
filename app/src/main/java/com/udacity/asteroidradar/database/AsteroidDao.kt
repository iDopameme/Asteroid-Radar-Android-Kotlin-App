package com.udacity.asteroidradar.database

import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: Asteroid)

    @Query("DELETE FROM asteroid_radar")
    suspend fun clearAsteroids()

    @Delete
    suspend fun deleteAsteroid(asteroid: Asteroid)

    @Query("SELECT * FROM asteroid_radar ORDER BY id")
    fun getAllAsteroids()
}