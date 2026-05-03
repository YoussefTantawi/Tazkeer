package com.tantawi.tazkeer.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// DAO contains the database operations for tasks.
@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query(
        """
        SELECT * FROM tasks
        WHERE isCompleted = 0
        AND (recurrenceType != 'Once' OR timeMillis BETWEEN :startOfDay AND :endOfDay)
        ORDER BY timeMillis ASC
        """
    )
    suspend fun getTodayTasks(startOfDay: Long, endOfDay: Long): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY timeMillis ASC")
    suspend fun getActiveTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks ORDER BY timeMillis ASC")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY completedAtMillis DESC")
    suspend fun getCompletedTasks(): List<TaskEntity>

    @Query(
        """
        SELECT * FROM tasks
        WHERE taskDate = :taskDate
        AND systemTaskType = :systemTaskType
        LIMIT 1
        """
    )
    suspend fun getSystemTaskForDate(taskDate: String, systemTaskType: String): TaskEntity?

    @Query(
        """
        SELECT * FROM tasks
        WHERE taskDate = :taskDate
        AND category = :category
        AND title = :title
        LIMIT 1
        """
    )
    suspend fun getTaskByDateCategoryAndTitle(taskDate: String, category: String, title: String): TaskEntity?

    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: Long): TaskEntity?

    @Query("UPDATE tasks SET isCompleted = 1, completedAtMillis = :completedAtMillis, updatedAtMillis = :completedAtMillis WHERE id = :taskId")
    suspend fun markTaskCompleted(taskId: Long, completedAtMillis: Long)

    @Query("UPDATE tasks SET isCompleted = 0, completedAtMillis = NULL, updatedAtMillis = :updatedAtMillis WHERE id = :taskId")
    suspend fun markTaskNotCompleted(taskId: Long, updatedAtMillis: Long)

    @Query("SELECT * FROM tasks WHERE isCompleted = 0 AND notificationEnabled = 1 ORDER BY timeMillis ASC")
    suspend fun getTasksForNotificationRescheduling(): List<TaskEntity>
}
