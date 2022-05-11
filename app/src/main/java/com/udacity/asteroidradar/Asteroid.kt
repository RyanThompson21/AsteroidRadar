package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.database.DatabaseAsteroid
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "asteroids")
data class Asteroid(
    @PrimaryKey val id: Long,
    val codename: String?,
    val closeApproachDate: String?,
    val absoluteMagnitude: Double?,
    val estimatedDiameter: Double?, // kilometers
    val relativeVelocity: Double?, // km/s
    val distanceFromEarth: Double?, // "miss_distance" astronomical
    val isPotentiallyHazardous: Boolean?
) : Parcelable

fun ArrayList<Asteroid>.asDataBaseModel(): Array<DatabaseAsteroid> {
    return this.map {
        DatabaseAsteroid(id = it.id,
        codename = it.codename,
        closeApproachDate = it.closeApproachDate,
        relativeVelocity = it.relativeVelocity,
        missDistance = it.distanceFromEarth,
        absoluteMagnitude = it.absoluteMagnitude,
        maxDiameter = it.estimatedDiameter,
        isPotentiallyHazardous = it.isPotentiallyHazardous)
    }.toTypedArray()
}