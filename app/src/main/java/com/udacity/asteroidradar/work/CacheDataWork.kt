package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.repository.Repository
import retrofit2.HttpException

// TODO cache the data of the asteroid by using a worker, so it downloads and saves today's
//  asteroids in background once a day when the device is charging and wifi is enabled.

class CacheDataWork(appContext: Context, params: WorkerParameters):
        CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "CacheDataWorker"
    }


    override suspend fun doWork(): Result {
        return try {
            val repo = Repository()
            repo.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}