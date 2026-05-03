package com.tantawi.tazkeer.helpers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.tantawi.tazkeer.database.AppDatabase
import com.tantawi.tazkeer.database.PrayerTimeEntity
import com.tantawi.tazkeer.database.TaskEntity
import com.tantawi.tazkeer.receivers.NotificationReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.abs

// Helper schedules and cancels AlarmManager reminders.
object AlarmHelper {
    const val EXTRA_CHANNEL_ID = "channelId"
    const val EXTRA_NOTIFICATION_TYPE = "notificationType"
    const val EXTRA_TASK_ID = "taskId"
    const val EXTRA_TASK_TITLE = "taskTitle"
    const val EXTRA_TASK_CATEGORY = "taskCategory"
    const val EXTRA_SYSTEM_TASK_TYPE = "systemTaskType"
    const val EXTRA_PRAYER_NAME = "prayerName"
    const val EXTRA_TASK_ALARM_KIND = "taskAlarmKind"
    const val TYPE_TASK = "task"
    const val TYPE_PRAYER = "prayer"
    const val KIND_BEFORE = "before"
    const val KIND_EXACT = "exact"

    private const val MINUS_TEN_MINUTES = 10L * 60L * 1000L

    fun scheduleTaskAlarms(context: Context, task: TaskEntity) {
        if (!PreferencesHelper.areNotificationsEnabled(context) || !task.notificationEnabled || task.isCompleted) return

        val beforeTime = task.timeMillis - MINUS_TEN_MINUTES
        val exactTime = task.timeMillis
        val taskTitle = task.title
        val normalizedCategory = DateTimeHelper.normalizeCategory(task.category)
        val isPrayerTask = normalizedCategory == DateTimeHelper.CATEGORY_PRAYER
        val channelId = if (isPrayerTask) NotificationHelper.CHANNEL_PRAYERS else NotificationHelper.CHANNEL_TASKS
        val notificationType = if (isPrayerTask) TYPE_PRAYER else TYPE_TASK
        val prayerName = if (isPrayerTask) {
            val rawName = task.systemTaskType
                ?.takeIf { it.startsWith("Prayer:") }
                ?.removePrefix("Prayer:")
                ?: task.title
            DateTimeHelper.canonicalPrayerName(rawName)
        } else {
            null
        }

        scheduleNotification(
            context,
            beforeTime,
            taskRequestCode(task.id, 1),
            channelId,
            task.id,
            taskTitle,
            task.category,
            task.systemTaskType,
            prayerName,
            notificationType,
            KIND_BEFORE
        )
        scheduleNotification(
            context,
            exactTime,
            taskRequestCode(task.id, 2),
            channelId,
            task.id,
            taskTitle,
            task.category,
            task.systemTaskType,
            prayerName,
            notificationType,
            KIND_EXACT
        )
    }

    fun cancelTaskAlarms(context: Context, taskId: Long) {
        cancelAlarm(context, taskRequestCode(taskId, 1))
        cancelAlarm(context, taskRequestCode(taskId, 2))
    }

    fun schedulePrayerAlarms(context: Context, prayerTime: PrayerTimeEntity) {
        if (!PreferencesHelper.areNotificationsEnabled(context)) return

        val prayers = listOf(
            "Fajr" to prayerTime.fajr,
            "Dhuhr" to prayerTime.dhuhr,
            "Asr" to prayerTime.asr,
            "Maghrib" to prayerTime.maghrib,
            "Isha" to prayerTime.isha
        )

        prayers.forEach { (name, time) ->
            val exactTime = DateTimeHelper.prayerTimeMillis(prayerTime.date, time)
            scheduleNotification(
                context,
                exactTime - MINUS_TEN_MINUTES,
                prayerRequestCode(prayerTime.date, name, 1),
                NotificationHelper.CHANNEL_PRAYERS,
                -1L,
                null,
                DateTimeHelper.CATEGORY_PRAYER,
                null,
                name,
                TYPE_PRAYER,
                KIND_BEFORE
            )
            scheduleNotification(
                context,
                exactTime,
                prayerRequestCode(prayerTime.date, name, 2),
                NotificationHelper.CHANNEL_PRAYERS,
                -1L,
                null,
                DateTimeHelper.CATEGORY_PRAYER,
                null,
                name,
                TYPE_PRAYER,
                KIND_EXACT
            )
        }
    }

    fun cancelPrayerAlarms(context: Context, prayerTime: PrayerTimeEntity) {
        listOf("Fajr", "Dhuhr", "Asr", "Maghrib", "Isha").forEach { name ->
            cancelAlarm(context, prayerRequestCode(prayerTime.date, name, 1))
            cancelAlarm(context, prayerRequestCode(prayerTime.date, name, 2))
        }
    }

    suspend fun rescheduleAll(context: Context) {
        withContext(Dispatchers.IO) {
            val database = AppDatabase.getDatabase(context)
            database.taskDao().getTasksForNotificationRescheduling()
                .filter { DateTimeHelper.shouldShowTaskToday(it) }
                .forEach { scheduleTaskAlarms(context, it) }
        }
    }

    fun cancelKnownAlarms(context: Context, tasks: List<TaskEntity>, prayerTime: PrayerTimeEntity?) {
        tasks.forEach { cancelTaskAlarms(context, it.id) }
        prayerTime?.let { cancelPrayerAlarms(context, it) }
    }

    private fun scheduleNotification(
        context: Context,
        triggerAtMillis: Long,
        requestCode: Int,
        channelId: String,
        taskId: Long,
        taskTitle: String?,
        taskCategory: String?,
        systemTaskType: String?,
        prayerName: String?,
        notificationType: String,
        taskAlarmKind: String
    ) {
        if (triggerAtMillis <= System.currentTimeMillis()) return

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(EXTRA_CHANNEL_ID, channelId)
            putExtra(EXTRA_NOTIFICATION_TYPE, notificationType)
            putExtra(EXTRA_TASK_ID, taskId)
            putExtra(EXTRA_TASK_TITLE, taskTitle)
            putExtra(EXTRA_TASK_CATEGORY, taskCategory)
            putExtra(EXTRA_SYSTEM_TASK_TYPE, systemTaskType)
            putExtra(EXTRA_PRAYER_NAME, prayerName)
            putExtra(EXTRA_TASK_ALARM_KIND, taskAlarmKind)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                // If exact alarms are restricted, a normal alarm keeps reminders working with slightly less precision.
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
            } else {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
            }
        } catch (_: SecurityException) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
        }
    }

    private fun cancelAlarm(context: Context, requestCode: Int) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun taskRequestCode(taskId: Long, type: Int): Int {
        return ((taskId % 100_000L) * 10L + type).toInt()
    }

    private fun prayerRequestCode(date: String, prayerName: String, type: Int): Int {
        return abs("$date-$prayerName-$type".hashCode())
    }
}
