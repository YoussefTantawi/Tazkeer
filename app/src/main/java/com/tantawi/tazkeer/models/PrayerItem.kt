package com.tantawi.tazkeer.models

data class PrayerItem(
    val nameKey: String,
    val displayName: String,
    val time: String,
    val timeMillis: Long,
    val completed: Boolean,
    val colorResId: Int,
    val priority: String
)
