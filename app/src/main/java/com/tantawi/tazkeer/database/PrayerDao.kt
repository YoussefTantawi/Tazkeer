package com.tantawi.tazkeer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// DAO contains the database operations for cached prayer times.
@Dao
interface PrayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePrayerTimes(prayerTime: PrayerTimeEntity)

    @Query("SELECT * FROM prayer_times WHERE date = :date AND latitude = :latitude AND longitude = :longitude LIMIT 1")
    suspend fun getPrayerTimesByDate(date: String, latitude: Double, longitude: Double): PrayerTimeEntity?

    @Query("SELECT * FROM prayer_times ORDER BY fetchedAtMillis DESC LIMIT 1")
    suspend fun getLatestPrayerTimes(): PrayerTimeEntity?

    @Query("DELETE FROM prayer_times WHERE fetchedAtMillis < :beforeMillis")
    suspend fun deleteOldPrayerTimes(beforeMillis: Long)
}
