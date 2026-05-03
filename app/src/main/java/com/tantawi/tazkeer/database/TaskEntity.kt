package com.tantawi.tazkeer.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity represents one table in the Room database.
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String?,
    val timeMillis: Long,
    val category: String,
    val customCategory: String?,
    val priority: String,
    val recurrenceType: String,
    val selectedDaysCsv: String,
    val isCompleted: Boolean,
    val completedAtMillis: Long?,
    val createdAtMillis: Long,
    val updatedAtMillis: Long,
    val notificationEnabled: Boolean,
    val taskDate: String = "",
    val isSystemTask: Boolean = false,
    val systemTaskType: String? = null
)
