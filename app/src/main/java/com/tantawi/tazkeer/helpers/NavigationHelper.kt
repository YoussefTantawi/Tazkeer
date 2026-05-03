package com.tantawi.tazkeer.helpers

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tantawi.tazkeer.CompletedTasksActivity
import com.tantawi.tazkeer.HomeActivity
import com.tantawi.tazkeer.R
import com.tantawi.tazkeer.SettingsActivity

// Helper keeps repeated navigation code readable.
object NavigationHelper {
    fun setupBottomNavigation(
        activity: Activity,
        bottomNavigationView: BottomNavigationView,
        selectedItemId: Int,
        sourceScreen: String
    ) {
        bottomNavigationView.selectedItemId = selectedItemId
        bottomNavigationView.setOnItemSelectedListener { item ->
            if (item.itemId == selectedItemId) return@setOnItemSelectedListener true

            val targetClass = when (item.itemId) {
                R.id.nav_home -> HomeActivity::class.java
                R.id.nav_completed -> CompletedTasksActivity::class.java
                R.id.nav_settings -> SettingsActivity::class.java
                else -> null
            } ?: return@setOnItemSelectedListener false

            val intent = Intent(activity, targetClass).apply {
                putExtra("sourceScreen", sourceScreen)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            activity.startActivity(intent)
            true
        }
    }

    fun openExternalIntent(context: Context, intent: Intent) {
        if (intent.resolveActivity(context.packageManager) != null) {
            try {
                context.startActivity(intent)
            } catch (_: ActivityNotFoundException) {
                Toast.makeText(context, R.string.external_app_missing, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, R.string.external_app_missing, Toast.LENGTH_SHORT).show()
        }
    }
}
