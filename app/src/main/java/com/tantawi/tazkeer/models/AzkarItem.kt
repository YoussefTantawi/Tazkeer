package com.tantawi.tazkeer.models

data class AzkarItem(
    val key: String,
    val titleResId: Int,
    val searchTermResId: Int,
    val completed: Boolean = false,
    val colorResId: Int,
    val sortOrder: Int,
    val priority: String
)
