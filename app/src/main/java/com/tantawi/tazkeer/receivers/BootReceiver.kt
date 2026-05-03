package com.tantawi.tazkeer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tantawi.tazkeer.helpers.AlarmHelper
import com.tantawi.tazkeer.helpers.PreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Receiver reschedules reminders after the device restarts.
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        if (!PreferencesHelper.areNotificationsEnabled(context)) return

        CoroutineScope(Dispatchers.IO).launch {
            AlarmHelper.rescheduleAll(context.applicationContext)
        }
    }
}
