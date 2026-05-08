package com.tantawi.tazkeer

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.tantawi.tazkeer.database.AppDatabase
import com.tantawi.tazkeer.helpers.AlarmHelper
import com.tantawi.tazkeer.helpers.DateTimeHelper
import com.tantawi.tazkeer.helpers.LanguageHelper
import com.tantawi.tazkeer.helpers.LocationHelper
import com.tantawi.tazkeer.helpers.NavigationHelper
import com.tantawi.tazkeer.helpers.NetworkHelper
import com.tantawi.tazkeer.helpers.PermissionHelper
import com.tantawi.tazkeer.helpers.PrayerApiHelper
import com.tantawi.tazkeer.helpers.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Activity for preferences, location, sharing, and app information.
class SettingsActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var notificationsSwitch: SwitchMaterial
    private lateinit var languageSwitch: SwitchMaterial
    private lateinit var timeFormatSwitch: SwitchMaterial
    private lateinit var summarySectionSwitch: SwitchMaterial
    private lateinit var upcomingSectionSwitch: SwitchMaterial
    private lateinit var sunrisePrayerSwitch: SwitchMaterial
    private lateinit var sunnahPrayersSwitch: SwitchMaterial
    private lateinit var nightPrayerSwitch: SwitchMaterial

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.wrapContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        PreferencesHelper.applyTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        database = AppDatabase.getDatabase(this)
        setSupportActionBar(findViewById<MaterialToolbar>(R.id.settingsToolbar))

        notificationsSwitch = findViewById(R.id.notificationsSwitch)
        languageSwitch = findViewById(R.id.languageSwitch)
        timeFormatSwitch = findViewById(R.id.timeFormatSwitch)
        summarySectionSwitch = findViewById(R.id.summarySectionSwitch)
        upcomingSectionSwitch = findViewById(R.id.upcomingSectionSwitch)
        sunrisePrayerSwitch = findViewById(R.id.sunrisePrayerSwitch)
        sunnahPrayersSwitch = findViewById(R.id.sunnahPrayersSwitch)
        nightPrayerSwitch = findViewById(R.id.nightPrayerSwitch)

        setupInitialValues()
        setupSettingsActions()

        NavigationHelper.setupBottomNavigation(
            this,
            findViewById(R.id.settingsBottomNavigation),
            R.id.nav_settings,
            "settings"
        )
    }

    private fun setupInitialValues() {
        findViewById<SwitchMaterial>(R.id.darkModeSwitch).isChecked = PreferencesHelper.isDarkMode(this)
        notificationsSwitch.isChecked = PreferencesHelper.areNotificationsEnabled(this)
        languageSwitch.isChecked = PreferencesHelper.getLanguage(this) == PreferencesHelper.LANGUAGE_ARABIC
        timeFormatSwitch.isChecked = PreferencesHelper.use24HourPrayerTime(this)
        summarySectionSwitch.isChecked = PreferencesHelper.isSummarySectionEnabled(this)
        upcomingSectionSwitch.isChecked = PreferencesHelper.isUpcomingSectionEnabled(this)
        sunrisePrayerSwitch.isChecked = PreferencesHelper.isSunrisePrayerEnabled(this)
        sunnahPrayersSwitch.isChecked = PreferencesHelper.isSunnahPrayersEnabled(this)
        nightPrayerSwitch.isChecked = PreferencesHelper.isNightPrayerEnabled(this)

        findViewById<TextView>(R.id.appInfoText).text = buildString {
            append(getString(R.string.app_name))
            append('\n')
            append(getString(R.string.app_version))
            append('\n')
            append(getString(R.string.app_description))
        }
    }

    private fun setupSettingsActions() {
        findViewById<SwitchMaterial>(R.id.darkModeSwitch).setOnCheckedChangeListener { _, isChecked ->
            PreferencesHelper.setDarkMode(this, isChecked)
            Toast.makeText(this, R.string.dark_mode_changed, Toast.LENGTH_SHORT).show()
            recreate()
        }

        languageSwitch.setOnCheckedChangeListener { _, isChecked ->
            val newLanguage = if (isChecked) {
                PreferencesHelper.LANGUAGE_ARABIC
            } else {
                PreferencesHelper.LANGUAGE_ENGLISH
            }
            if (newLanguage != PreferencesHelper.getLanguage(this)) {
                PreferencesHelper.setLanguage(this, newLanguage)
                Toast.makeText(this, R.string.language_changed, Toast.LENGTH_SHORT).show()
                restartAppToHome()
            }
        }

        timeFormatSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferencesHelper.setUse24HourPrayerTime(this, isChecked)
            Toast.makeText(this, R.string.time_format_changed, Toast.LENGTH_SHORT).show()
        }

        summarySectionSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferencesHelper.setSummarySectionEnabled(this, isChecked)
        }

        upcomingSectionSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferencesHelper.setUpcomingSectionEnabled(this, isChecked)
        }

        sunrisePrayerSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferencesHelper.setSunrisePrayerEnabled(this, isChecked)
        }

        sunnahPrayersSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferencesHelper.setSunnahPrayersEnabled(this, isChecked)
        }

        nightPrayerSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferencesHelper.setNightPrayerEnabled(this, isChecked)
        }

        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferencesHelper.setNotificationsEnabled(this, isChecked)
            if (isChecked) {
                if (PermissionHelper.requestNotificationsIfNeeded(this)) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) { AlarmHelper.rescheduleAll(this@SettingsActivity) }
                        Toast.makeText(this@SettingsActivity, R.string.notifications_enabled, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val tasks = database.taskDao().getTasksForNotificationRescheduling()
                        val prayer = database.prayerDao().getLatestPrayerTimes()
                        AlarmHelper.cancelKnownAlarms(this@SettingsActivity, tasks, prayer)
                    }
                    Toast.makeText(this@SettingsActivity, R.string.notifications_disabled, Toast.LENGTH_SHORT).show()
                }
            }
        }

        findViewById<MaterialButton>(R.id.updateLocationButton).setOnClickListener {
            updateLocationAutomatically()
        }
        findViewById<MaterialButton>(R.id.contactUsButton).setOnClickListener {
            contactUs()
        }
        findViewById<MaterialButton>(R.id.shareAppButton).setOnClickListener {
            shareApp()
        }
        findViewById<MaterialButton>(R.id.resetPreferencesButton).setOnClickListener {
            resetPreferences()
        }
    }

    private fun updateLocationAutomatically() {
        if (!PermissionHelper.hasLocationPermission(this)) {
            PermissionHelper.requestLocationPermission(this)
            return
        }
        if (!LocationHelper.isLocationEnabled(this)) {
            Toast.makeText(this, R.string.location_services_disabled, Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            return
        }
        LocationHelper.getCurrentLocation(
            this,
            onSuccess = { latitude, longitude ->
                PreferencesHelper.setLocation(this, latitude, longitude)
                Toast.makeText(this, R.string.location_updated, Toast.LENGTH_SHORT).show()
                refreshPrayerTimes(latitude, longitude)
            },
            onError = {
                Toast.makeText(this, R.string.location_unavailable, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun refreshPrayerTimes(latitude: Double, longitude: Double) {
        lifecycleScope.launch {
            val date = DateTimeHelper.apiDate()
            val prayerTime = withContext(Dispatchers.IO) {
                if (NetworkHelper.isInternetAvailable(this@SettingsActivity)) {
                    PrayerApiHelper.fetchPrayerTimes(date, latitude, longitude)?.also {
                        database.prayerDao().insertOrUpdatePrayerTimes(it)
                    }
                } else {
                    database.prayerDao().getLatestPrayerTimes()
                }
            }

            if (prayerTime == null) {
                Toast.makeText(this@SettingsActivity, R.string.no_internet_no_cache, Toast.LENGTH_SHORT).show()
            } else {
                if (!NetworkHelper.isInternetAvailable(this@SettingsActivity)) {
                    Toast.makeText(this@SettingsActivity, R.string.no_internet_using_cache, Toast.LENGTH_SHORT).show()
                }
                AlarmHelper.schedulePrayerAlarms(this@SettingsActivity, prayerTime)
            }
        }
    }

    private fun contactUs() {
        val message = getString(R.string.contact_message)
        val whatsappUrl = "${getString(R.string.whatsapp_fallback)}?text=${Uri.encode(message)}"
        val whatsAppIntent = Intent(Intent.ACTION_VIEW, whatsappUrl.toUri()).apply {
            setPackage("com.whatsapp")
            putExtra(Intent.EXTRA_TEXT, message)
        }

        if (whatsAppIntent.resolveActivity(packageManager) != null) {
            NavigationHelper.openExternalIntent(this, whatsAppIntent)
        } else {
            NavigationHelper.openExternalIntent(this, Intent(Intent.ACTION_VIEW, whatsappUrl.toUri()))
        }
    }

    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
        }
        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_app)))
        } else {
            Toast.makeText(this, R.string.external_app_missing, Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetPreferences() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val tasks = database.taskDao().getTasksForNotificationRescheduling()
                val prayer = database.prayerDao().getLatestPrayerTimes()
                AlarmHelper.cancelKnownAlarms(this@SettingsActivity, tasks, prayer)
            }
            PreferencesHelper.reset(this@SettingsActivity)
            Toast.makeText(this@SettingsActivity, R.string.preferences_reset, Toast.LENGTH_SHORT).show()
            restartAppToHome()
        }
    }

    private fun restartAppToHome() {
        val intent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionHelper.REQUEST_LOCATION) {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                updateLocationAutomatically()
            } else {
                Toast.makeText(this, R.string.permission_required, Toast.LENGTH_SHORT).show()
            }
        }
        if (requestCode == PermissionHelper.REQUEST_NOTIFICATIONS) {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) { AlarmHelper.rescheduleAll(this@SettingsActivity) }
                    Toast.makeText(this@SettingsActivity, R.string.notifications_enabled, Toast.LENGTH_SHORT).show()
                }
            } else {
                notificationsSwitch.isChecked = false
                Toast.makeText(this, R.string.permission_required, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
