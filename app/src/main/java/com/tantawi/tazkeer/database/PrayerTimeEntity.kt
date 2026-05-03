package com.tantawi.tazkeer.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Entity represents cached prayer times for one date and location.
@Entity(
    tableName = "prayer_times",
    indices = [Index(value = ["date", "latitude", "longitude"], unique = true)]
)
data class PrayerTimeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val fajr: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val sunrise: String?,
    val fetchedAtMillis: Long
)
