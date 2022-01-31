package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroid: Asteroid)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertPicture(pictureOfDay: PictureOfDay)
//
//    @Query("SELECT * FROM picture_of_day WHERE ")
//    fun getPictureOfDay()

    @Query("DELETE FROM asteroid_radar")
    fun clearAsteroids()

    @Delete
    fun deleteAsteroid(asteroid: Asteroid)

    @Query("SELECT * FROM asteroid_radar ORDER BY close_approach_date ASC ")
    fun getAllAsteroids(): List<Asteroid>

    @Query("SELECT * FROM asteroid_radar WHERE close_approach_date = :today")
    fun getTodaysAsteroids(today: String): List<Asteroid>

    @Query("SELECT * FROM asteroid_radar WHERE close_approach_date >= :today ORDER BY close_approach_date ASC")
    fun getWeekAsteroids(today: String): List<Asteroid>
}