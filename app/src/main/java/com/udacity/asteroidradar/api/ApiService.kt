package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.PictureOfDay
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("neo/rest/v1/feed")
    suspend fun requestNeoWs(@Query("start_date") startDate: String,
                             @Query("api_key") apiKey: String): String

    @GET("planetary/apod")
    suspend fun requestImageOfDay(@Query("api_key") apiKey: String) : PictureOfDay

}