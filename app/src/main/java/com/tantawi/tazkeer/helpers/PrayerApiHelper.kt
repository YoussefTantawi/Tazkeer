package com.tantawi.tazkeer.helpers

import com.tantawi.tazkeer.database.PrayerTimeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

// Helper fetches prayer times from the API without Retrofit.
object PrayerApiHelper {
    suspend fun fetchPrayerTimes(date: String, latitude: Double, longitude: Double): PrayerTimeEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val urlText = String.format(
                    Locale.US,
                    "https://api.aladhan.com/v1/timings/%s?latitude=%f&longitude=%f",
                    date,
                    latitude,
                    longitude
                )
                val connection = URL(urlText).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10_000
                connection.readTimeout = 10_000

                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    connection.disconnect()
                    return@withContext null
                }

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                connection.disconnect()

                val timings = JSONObject(response)
                    .getJSONObject("data")
                    .getJSONObject("timings")

                PrayerTimeEntity(
                    date = date,
                    latitude = latitude,
                    longitude = longitude,
                    fajr = DateTimeHelper.stripApiTime(timings.optString("Fajr")),
                    dhuhr = DateTimeHelper.stripApiTime(timings.optString("Dhuhr")),
                    asr = DateTimeHelper.stripApiTime(timings.optString("Asr")),
                    maghrib = DateTimeHelper.stripApiTime(timings.optString("Maghrib")),
                    isha = DateTimeHelper.stripApiTime(timings.optString("Isha")),
                    sunrise = DateTimeHelper.stripApiTime(timings.optString("Sunrise")),
                    midnight = DateTimeHelper.stripApiTime(timings.optString("Midnight")),
                    fetchedAtMillis = System.currentTimeMillis()
                )
            } catch (_: Exception) {
                null
            }
        }
    }
}
