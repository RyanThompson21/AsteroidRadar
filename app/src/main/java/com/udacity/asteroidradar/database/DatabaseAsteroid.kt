package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.*


@Entity
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val id: Long,
    val codename: String?,
    val closeApproachDate: String?,
    val relativeVelocity: Double?,
    val missDistance: Double?,
    val absoluteMagnitude: Double?,
    val maxDiameter: Double?,
    val isPotentiallyHazardous: Boolean?
)

/**
 * This function maps database objects to domain objects
 */
fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(id = it.id, codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.maxDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.missDistance,
            isPotentiallyHazardous = it.isPotentiallyHazardous)
    }

}