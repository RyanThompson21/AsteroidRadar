package com.udacity.asteroidradar

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "picOfDay")
data class PictureOfDay(@Json(name = "media_type") val mediaType: String,
                        val title: String,
                        @PrimaryKey
                        val url: String)