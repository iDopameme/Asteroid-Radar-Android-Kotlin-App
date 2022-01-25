package com.udacity.asteroidradar.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.ImageOfDay
import com.udacity.asteroidradar.api.RetrofitInstance
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val _asteroids: MutableLiveData<List<Asteroid>> = MutableLiveData()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _asteroidImage: MutableLiveData<ImageOfDay> = MutableLiveData()
    val asteroidImage: LiveData<ImageOfDay>
        get() = _asteroidImage

    // Handles navigation to the selected asteroid
    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    init {
        loadAsteroids()
    }

    fun loadAsteroids() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val queuedAsteroid = RetrofitInstance.api.requestNeoWs("2022-01-24",
                    Constants.API_KEY)
                val queuedImageOfDay = RetrofitInstance.imageApi.requestImageOfDay(Constants.API_KEY)

                Log.i(TAG, "Got asteroids: $queuedAsteroid")
                Log.i(TAG, "Got Image of Day: $queuedImageOfDay")

                val asteroidJSON = JSONObject(queuedAsteroid)
                _asteroids.value = parseAsteroidsJsonResult(asteroidJSON)

                _asteroidImage.value = queuedImageOfDay

            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }
}