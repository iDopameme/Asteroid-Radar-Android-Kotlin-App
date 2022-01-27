package com.udacity.asteroidradar.database

import com.squareup.moshi.Json

// Data class holding Picture of the day which is displayed on the MainFragment
data class PictureOfDay(@Json(name = "media_type") val mediaType: String, val title: String,
                        val url: String)