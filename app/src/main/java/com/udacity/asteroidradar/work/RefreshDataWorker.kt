package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase
import repository.AsteroidRepository
import retrofit2.HttpException

class RefreshDataWorker (context: Context, params: WorkerParameters):
CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val db = AsteroidDatabase.getInstance(applicationContext)
        val repo = AsteroidRepository(db)
        return try{
            repo.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
}