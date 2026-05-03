package com.tantawi.tazkeer.helpers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.tantawi.tazkeer.HomeActivity
import com.tantawi.tazkeer.R
import com.tantawi.tazkeer.receivers.NotificationReceiver

// Helper creates channels and displays notifications safely.
object NotificationHelper {
    const val CHANNEL_TASKS = "tasks_channel"
    const val CHANNEL_PRAYERS = "prayers_channel"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val localizedContext = LanguageHelper.wrapContext(context)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val taskChannel = NotificationChannel(
            CHANNEL_TASKS,
            localizedContext.getString(R.string.notification_channel_tasks),
            NotificationManager.IMPORTANCE_HIGH
        )
        val prayerChannel = NotificationChannel(
            CHANNEL_PRAYERS,
            localizedContext.getString(R.string.notification_channel_prayers),
            NotificationManager.IMPORTANCE_HIGH
        )
        manager.createNotificationChannel(taskChannel)
        manager.createNotificationChannel(prayerChannel)
    }

    fun showNotification(
        context: Context,
        channelId: String,
        title: String,
        body: String,
        notificationType: String,
        taskId: Long
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        createChannels(context)
        val localizedContext = LanguageHelper.wrapContext(context)
        val notificationId = if (taskId > 0L) {
            (taskId % Int.MAX_VALUE).toInt()
        } else {
            (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
        }
        val homeIntent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("openedFromNotification", true)
            putExtra("notificationType", notificationType)
        }
        val contentIntent = PendingIntent.getActivity(
            context,
            notificationId + 1_000,
            homeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setColor(ContextCompat.getColor(context, R.color.green))
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)

        if (taskId > 0L) {
            val completeIntent = Intent(context, NotificationReceiver::class.java).apply {
                action = NotificationReceiver.ACTION_MARK_TASK_COMPLETED
                putExtra(AlarmHelper.EXTRA_TASK_ID, taskId)
                putExtra(NotificationReceiver.EXTRA_NOTIFICATION_ID, notificationId)
            }
            val completePendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId + 2_000,
                completeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            builder.addAction(
                R.drawable.ic_completed,
                localizedContext.getString(R.string.notification_action_mark_completed),
                completePendingIntent
            )

            val detailsIntent = Intent(context, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra("openTaskDetails", true)
                putExtra("taskId", taskId)
            }
            val detailsPendingIntent = PendingIntent.getActivity(
                context,
                notificationId + 3_000,
                detailsIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            builder.addAction(
                R.drawable.ic_home,
                localizedContext.getString(R.string.notification_action_show_details),
                detailsPendingIntent
            )
        }

        NotificationManagerCompat.from(context)
            .notify(notificationId, builder.build())
    }
}
