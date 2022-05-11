package com.udacity.asteroidradar

import com.squareup.moshi.Json

data class AsteroidData(
    @Json(name = "near_earth_objects")
    val data: Map<String,List<Asteroid>>)
