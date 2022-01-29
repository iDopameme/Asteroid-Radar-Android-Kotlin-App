package com.udacity.asteroidradar.repository

import android.app.Application
import android.os.Build
import androidx.room.Room
import androidx.work.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.work.CacheDataWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication : Application() {

    private val DB_NAME = "asteroid_database"
    val applicationScope = CoroutineScope(Dispatchers.Default)

    companion object {
        lateinit var database: AsteroidDatabase
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest =
            PeriodicWorkRequestBuilder<CacheDataWork>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            CacheDataWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }




    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AsteroidDatabase::class.java, DB_NAME)
            .build()
        delayedInit()
    }
}