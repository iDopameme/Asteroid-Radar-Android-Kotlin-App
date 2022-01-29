package com.udacity.asteroidradar.main

import android.content.ContentValues.TAG
import android.graphics.Picture
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.RetrofitInstance
import com.udacity.asteroidradar.api.getCurrentDate
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val repository: AsteroidRepository) : ViewModel() {
    private val _asteroids: MutableLiveData<List<Asteroid>> = MutableLiveData()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _asteroidImage: MutableLiveData<PictureOfDay> = MutableLiveData()
    val asteroidImage: LiveData<PictureOfDay>
        get() = _asteroidImage

    // Handles navigation to the selected asteroid
    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid?>()
    val navigateToSelectedAsteroid: LiveData<Asteroid?>
        get() = _navigateToSelectedAsteroid

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isImageLoading = MutableLiveData(false)
    val isImageLoading: LiveData<Boolean>
        get() = _isImageLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _imageErrorMessage = MutableLiveData<String?>(null)
    val imageErrorMessage: LiveData<String?>
        get() = _imageErrorMessage

    init {
        viewModelScope.launch {
            //verifyTodayLocalAsteroid()
            loadImageOfDay()
            loadAsteroids()
        }
    }

    // TODO Use Work Manager to fetch new asteroids from API *ONLY* use database to fetch local
    //  queries. Check if local database exists

    private suspend fun loadAsteroids() {
        _asteroids.value = loadLocalAsteroids()
    }

    private suspend fun loadLocalAsteroids(): List<Asteroid> {
        val localAsteroids: List<Asteroid>
        withContext(Dispatchers.IO) {
            localAsteroids = repository.getSavedAsteroids()
        }
        return localAsteroids
    }

    private fun loadImageOfDay() {
        viewModelScope.launch {
            _isImageLoading.value = true
            try {
                val queuedImageOfDay =
                    RetrofitInstance.imageApi.requestImageOfDay(Constants.API_KEY)
                Log.i(TAG, "Got Image of Day: $queuedImageOfDay")
                _asteroidImage.value = queuedImageOfDay
            } catch (e: Exception) {
                _imageErrorMessage.value = e.message
                Log.e(TAG, "Exception $e")
            } finally {
                _isImageLoading.value = false
            }
        }
    }

    suspend fun verifyTodayLocalAsteroid() {
        withContext(Dispatchers.IO) {
            val currentAsteroidCheck = repository.getTodaysAsteroid()

            println("Checking if today's asteroid is in local: $currentAsteroidCheck")
        }
    }

    private fun loadAPIAsteroids() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true

            _asteroids.value = repository.getSavedAsteroids()
            try {
                val asteroidList: ArrayList<Asteroid>
                val queuedAsteroid = RetrofitInstance.api.requestNeoWs(getCurrentDate(), Constants.API_KEY)
                Log.i(TAG, "Got asteroids: $queuedAsteroid")

                val asteroidJSON = JSONObject(queuedAsteroid)
                asteroidList = parseAsteroidsJsonResult(asteroidJSON)
                _asteroids.value = asteroidList

                for (asteroid in asteroidList) {
                    withContext(Dispatchers.IO) {
                        repository.saveAsteroid(asteroid)
                    }
                }
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