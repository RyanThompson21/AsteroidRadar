package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsteroids(asteroids: Array<DatabaseAsteroid>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPic(pic: PictureOfDay?)

    @Query("select * from picOfDay")
    fun getPic(): LiveData<PictureOfDay>

    @Query("select * from asteroids where closeApproachDate =:todayDate " +
            "order by closeApproachDate")
    fun getTodaysAsteroids(todayDate: String): LiveData<List<Asteroid>>

    @Query("select * from asteroids where closeApproachDate =:todayDate " +
            "and isPotentiallyHazardous=:isHazardous ")
    fun getPotentiallyHazardousFromToday(
        todayDate: String,
        isHazardous: Boolean
    ): LiveData<List<Asteroid>>
    @Query("select * from asteroids where closeApproachDate>=:todayDate " +
            "order by closeApproachDate")
    fun getAllAsteroids(todayDate: String): LiveData<List<Asteroid>>
}