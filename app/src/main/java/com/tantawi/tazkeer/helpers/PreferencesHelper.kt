package com.tantawi.tazkeer.helpers

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

// Helper keeps SharedPreferences reads and writes in one simple place.
object PreferencesHelper {
    private const val PREFS_NAME = "tazkeer_preferences"
    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_LANGUAGE = "language"
    private const val KEY_NOTIFICATIONS = "notifications"
    private const val KEY_LATITUDE = "latitude"
    private const val KEY_LONGITUDE = "longitude"
    private const val KEY_USE_24_HOUR_TIME = "use_24_hour_time"
    private const val KEY_LAST_RESET_DATE = "last_reset_date"
    private const val KEY_SUMMARY_SECTION_ENABLED = "summary_section_enabled"
    private const val KEY_UPCOMING_SECTION_ENABLED = "upcoming_section_enabled"
    private const val KEY_SECTION_VISIBLE_PREFIX = "section_visible_"
    private const val KEY_PRAYER_COMPLETED_PREFIX = "prayer_completed_"
    private const val KEY_AZKAR_COMPLETED_PREFIX = "azkar_completed_"
    private const val KEY_SYSTEM_TASK_DELETED_PREFIX = "system_task_deleted_"

    const val DEFAULT_LATITUDE = 30.0444
    const val DEFAULT_LONGITUDE = 31.2357
    const val LANGUAGE_ENGLISH = "en"
    const val LANGUAGE_ARABIC = "ar"

    private fun prefs(context: Context) = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isDarkMode(context: Context): Boolean = prefs(context).getBoolean(KEY_DARK_MODE, false)

    fun setDarkMode(context: Context, enabled: Boolean) {
        prefs(context).edit { putBoolean(KEY_DARK_MODE, enabled) }
    }

    fun applyTheme(context: Context) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode(context)) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun getLanguage(context: Context): String = prefs(context).getString(KEY_LANGUAGE, LANGUAGE_ENGLISH) ?: LANGUAGE_ENGLISH

    fun setLanguage(context: Context, language: String) {
        prefs(context).edit { putString(KEY_LANGUAGE, language) }
    }

    fun areNotificationsEnabled(context: Context): Boolean = prefs(context).getBoolean(KEY_NOTIFICATIONS, true)

    fun setNotificationsEnabled(context: Context, enabled: Boolean) {
        prefs(context).edit { putBoolean(KEY_NOTIFICATIONS, enabled) }
    }

    fun use24HourPrayerTime(context: Context): Boolean = prefs(context).getBoolean(KEY_USE_24_HOUR_TIME, false)

    fun setUse24HourPrayerTime(context: Context, enabled: Boolean) {
        prefs(context).edit { putBoolean(KEY_USE_24_HOUR_TIME, enabled) }
    }

    fun isSummarySectionEnabled(context: Context): Boolean {
        return prefs(context).getBoolean(KEY_SUMMARY_SECTION_ENABLED, true)
    }

    fun setSummarySectionEnabled(context: Context, enabled: Boolean) {
        prefs(context).edit { putBoolean(KEY_SUMMARY_SECTION_ENABLED, enabled) }
    }

    fun isUpcomingSectionEnabled(context: Context): Boolean {
        return prefs(context).getBoolean(KEY_UPCOMING_SECTION_ENABLED, true)
    }

    fun setUpcomingSectionEnabled(context: Context, enabled: Boolean) {
        prefs(context).edit { putBoolean(KEY_UPCOMING_SECTION_ENABLED, enabled) }
    }

    fun getLatitude(context: Context): Double {
        return prefs(context).getString(KEY_LATITUDE, DEFAULT_LATITUDE.toString())?.toDoubleOrNull() ?: DEFAULT_LATITUDE
    }

    fun getLongitude(context: Context): Double {
        return prefs(context).getString(KEY_LONGITUDE, DEFAULT_LONGITUDE.toString())?.toDoubleOrNull() ?: DEFAULT_LONGITUDE
    }

    fun setLocation(context: Context, latitude: Double, longitude: Double) {
        prefs(context).edit {
            putString(KEY_LATITUDE, latitude.toString())
                .putString(KEY_LONGITUDE, longitude.toString())
        }
    }

    fun isSectionVisible(context: Context, sectionKey: String): Boolean {
        return prefs(context).getBoolean("$KEY_SECTION_VISIBLE_PREFIX$sectionKey", true)
    }

    fun setSectionVisible(context: Context, sectionKey: String, visible: Boolean) {
        prefs(context).edit { putBoolean("$KEY_SECTION_VISIBLE_PREFIX$sectionKey", visible) }
    }

    fun resetDailyCompletionsIfNeeded(context: Context, currentDate: String) {
        val sharedPreferences = prefs(context)
        if (sharedPreferences.getString(KEY_LAST_RESET_DATE, null) == currentDate) return

        sharedPreferences.edit {
            sharedPreferences.all.keys
                .filter {
                    it.startsWith(KEY_PRAYER_COMPLETED_PREFIX) ||
                        it.startsWith(KEY_AZKAR_COMPLETED_PREFIX) ||
                        it.startsWith(KEY_SYSTEM_TASK_DELETED_PREFIX)
                }
                .forEach { remove(it) }
            putString(KEY_LAST_RESET_DATE, currentDate)
        }
    }

    fun isSystemTaskDeleted(context: Context, date: String, systemTaskType: String): Boolean {
        return prefs(context).getBoolean("$KEY_SYSTEM_TASK_DELETED_PREFIX${date}_$systemTaskType", false)
    }

    fun setSystemTaskDeleted(context: Context, date: String, systemTaskType: String, deleted: Boolean) {
        prefs(context).edit { putBoolean("$KEY_SYSTEM_TASK_DELETED_PREFIX${date}_$systemTaskType", deleted) }
    }

    fun reset(context: Context) {
        val sharedPreferences = prefs(context)
        sharedPreferences.edit {
            sharedPreferences.all.keys
                .filter {
                    it.startsWith(KEY_SECTION_VISIBLE_PREFIX) ||
                        it.startsWith(KEY_PRAYER_COMPLETED_PREFIX) ||
                        it.startsWith(KEY_AZKAR_COMPLETED_PREFIX) ||
                        it.startsWith(KEY_SYSTEM_TASK_DELETED_PREFIX)
                }
                .forEach { remove(it) }
            putBoolean(KEY_DARK_MODE, false)
                .putString(KEY_LANGUAGE, LANGUAGE_ENGLISH)
                .putBoolean(KEY_NOTIFICATIONS, true)
                .putString(KEY_LATITUDE, DEFAULT_LATITUDE.toString())
                .putString(KEY_LONGITUDE, DEFAULT_LONGITUDE.toString())
                .putBoolean(KEY_USE_24_HOUR_TIME, false)
                .putBoolean(KEY_SUMMARY_SECTION_ENABLED, true)
                .putBoolean(KEY_UPCOMING_SECTION_ENABLED, true)
                .remove(KEY_LAST_RESET_DATE)
        }
    }
}
