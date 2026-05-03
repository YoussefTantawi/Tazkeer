package com.tantawi.tazkeer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.tantawi.tazkeer.database.AppDatabase
import com.tantawi.tazkeer.helpers.AlarmHelper
import com.tantawi.tazkeer.helpers.DateTimeHelper
import com.tantawi.tazkeer.helpers.LanguageHelper
import com.tantawi.tazkeer.helpers.NotificationHelper
import com.tantawi.tazkeer.helpers.PreferencesHelper
import com.tantawi.tazkeer.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Receiver responds when AlarmManager fires a reminder.
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_MARK_TASK_COMPLETED) {
            markTaskCompleted(context.applicationContext, intent)
            return
        }

        if (!PreferencesHelper.areNotificationsEnabled(context)) return

        val localizedContext = LanguageHelper.wrapContext(context)
        val channelId = intent.getStringExtra(AlarmHelper.EXTRA_CHANNEL_ID) ?: NotificationHelper.CHANNEL_TASKS
        val notificationType = intent.getStringExtra(AlarmHelper.EXTRA_NOTIFICATION_TYPE)
            ?: if (channelId == NotificationHelper.CHANNEL_PRAYERS) AlarmHelper.TYPE_PRAYER else AlarmHelper.TYPE_TASK
        val alarmKind = intent.getStringExtra(AlarmHelper.EXTRA_TASK_ALARM_KIND)
        val title = notificationTitle(localizedContext, notificationType, alarmKind)
        val body = notificationBody(localizedContext, intent, notificationType, alarmKind)
        val taskId = intent.getLongExtra(AlarmHelper.EXTRA_TASK_ID, -1L)
        NotificationHelper.showNotification(context, channelId, title, body, notificationType, taskId)

        if (taskId > 0 && alarmKind == AlarmHelper.KIND_EXACT) {
            rescheduleRecurringTask(context.applicationContext, taskId)
        }
    }

    private fun notificationTitle(context: Context, notificationType: String, alarmKind: String?): String {
        return when (notificationType) {
            AlarmHelper.TYPE_PRAYER -> {
                if (alarmKind == AlarmHelper.KIND_EXACT) {
                    context.getString(R.string.prayer_time_title)
                } else {
                    context.getString(R.string.prayer_reminder_title)
                }
            }
            else -> {
                if (alarmKind == AlarmHelper.KIND_EXACT) {
                    context.getString(R.string.task_time_title)
                } else {
                    context.getString(R.string.task_reminder_title)
                }
            }
        }
    }

    private fun notificationBody(
        context: Context,
        intent: Intent,
        notificationType: String,
        alarmKind: String?
    ): String {
        return when (notificationType) {
            AlarmHelper.TYPE_PRAYER -> {
                val prayerName = intent.getStringExtra(AlarmHelper.EXTRA_PRAYER_NAME).orEmpty()
                val displayName = DateTimeHelper.localizedPrayerName(context, prayerName)
                if (alarmKind == AlarmHelper.KIND_EXACT) {
                    context.getString(R.string.prayer_exact_body, displayName)
                } else {
                    context.getString(R.string.prayer_before_body, displayName)
                }
            }
            else -> {
                val taskTitle = DateTimeHelper.localizedTaskTitle(
                    context,
                    intent.getStringExtra(AlarmHelper.EXTRA_TASK_TITLE).orEmpty(),
                    intent.getStringExtra(AlarmHelper.EXTRA_TASK_CATEGORY).orEmpty(),
                    intent.getStringExtra(AlarmHelper.EXTRA_SYSTEM_TASK_TYPE)
                )
                if (alarmKind == AlarmHelper.KIND_EXACT) {
                    context.getString(R.string.task_exact_body, taskTitle)
                } else {
                    context.getString(R.string.task_before_body, taskTitle)
                }
            }
        }
    }

    private fun markTaskCompleted(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val taskId = intent.getLongExtra(AlarmHelper.EXTRA_TASK_ID, -1L)
                val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1)
                if (taskId > 0L) {
                    val dao = AppDatabase.getDatabase(context).taskDao()
                    val task = dao.getTaskById(taskId)
                    if (task != null && !task.isCompleted) {
                        dao.markTaskCompleted(taskId, System.currentTimeMillis())
                        AlarmHelper.cancelTaskAlarms(context, taskId)
                    }
                }
                if (notificationId >= 0) {
                    NotificationManagerCompat.from(context).cancel(notificationId)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }

    private fun rescheduleRecurringTask(context: Context, taskId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.getDatabase(context).taskDao()
            val task = dao.getTaskById(taskId) ?: return@launch
            if (task.isCompleted || task.recurrenceType == DateTimeHelper.RECURRENCE_ONCE) return@launch

            val nextTime = DateTimeHelper.nextOccurrenceAfter(task, System.currentTimeMillis())
            val updatedTask = task.copy(
                timeMillis = nextTime,
                updatedAtMillis = System.currentTimeMillis()
            )
            dao.updateTask(updatedTask)
            AlarmHelper.scheduleTaskAlarms(context, updatedTask)
        }
    }

    companion object {
        const val ACTION_MARK_TASK_COMPLETED = "com.tantawi.tazkeer.ACTION_MARK_TASK_COMPLETED"
        const val EXTRA_NOTIFICATION_ID = "notificationId"
    }
}
