package com.tantawi.tazkeer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tantawi.tazkeer.adapters.SummaryAdapter
import com.tantawi.tazkeer.adapters.TaskAdapter
import com.tantawi.tazkeer.database.AppDatabase
import com.tantawi.tazkeer.database.PrayerTimeEntity
import com.tantawi.tazkeer.database.TaskEntity
import com.tantawi.tazkeer.fragments.AddEditTaskFragment
import com.tantawi.tazkeer.fragments.TaskDetailsFragment
import com.tantawi.tazkeer.helpers.AlarmHelper
import com.tantawi.tazkeer.helpers.DateTimeHelper
import com.tantawi.tazkeer.helpers.LanguageHelper
import com.tantawi.tazkeer.helpers.NavigationHelper
import com.tantawi.tazkeer.helpers.NetworkHelper
import com.tantawi.tazkeer.helpers.NotificationHelper
import com.tantawi.tazkeer.helpers.PermissionHelper
import com.tantawi.tazkeer.helpers.PrayerApiHelper
import com.tantawi.tazkeer.helpers.PreferencesHelper
import com.tantawi.tazkeer.models.SummaryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import androidx.core.net.toUri

// Launcher Activity that shows today's summary and active task sections.
class HomeActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var summaryAdapter: SummaryAdapter
    private lateinit var emptyTasksText: TextView
    private lateinit var addEditContainer: View
    private lateinit var summarySection: View
    private lateinit var summaryRecyclerView: RecyclerView
    private lateinit var taskSectionsContainer: LinearLayout
    private lateinit var upcomingSection: View
    private lateinit var upcomingCard: View
    private lateinit var upcomingEmptyText: TextView
    private lateinit var upcomingTitleText: TextView
    private lateinit var upcomingTimeText: TextView
    private lateinit var upcomingCountdownText: TextView

    private var currentTasks: List<TaskEntity> = emptyList()
    private var todayTasksForSummary: List<TaskEntity> = emptyList()
    private var currentPrayerTimesByName: Map<String, Long> = emptyMap()
    private var currentSort = SortType.TIME
    private var firstResume = true
    private val upcomingHandler = Handler(Looper.getMainLooper())
    private val upcomingRefreshRunnable = object : Runnable {
        override fun run() {
            renderUpcomingTask()
            upcomingHandler.postDelayed(this, UPCOMING_REFRESH_DELAY)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.wrapContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        PreferencesHelper.applyTheme(this)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        database = AppDatabase.getDatabase(this)
        NotificationHelper.createChannels(this)
        PermissionHelper.requestNotificationsIfNeeded(this)
        resetDailyCompletions()

        val toolbar = findViewById<MaterialToolbar>(R.id.homeToolbar)
        setSupportActionBar(toolbar)

        emptyTasksText = findViewById(R.id.emptyTasksText)
        addEditContainer = findViewById(R.id.addEditContainer)
        summarySection = findViewById(R.id.summarySection)
        taskSectionsContainer = findViewById(R.id.taskSectionsContainer)
        upcomingSection = findViewById(R.id.upcomingSection)
        upcomingCard = findViewById(R.id.upcomingCard)
        upcomingEmptyText = findViewById(R.id.upcomingEmptyText)
        upcomingTitleText = findViewById(R.id.upcomingTaskTitleText)
        upcomingTimeText = findViewById(R.id.upcomingTaskTimeText)
        upcomingCountdownText = findViewById(R.id.upcomingTaskCountdownText)

        setupRecyclerViews()
        setupBottomNavigation()
        setupAddButton()

        loadTasks()
        loadPrayerTimes()
        handleDetailsIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        applySummaryVisibility()
        applyUpcomingVisibility()
        startUpcomingTicker()
        if (firstResume) {
            firstResume = false
        } else {
            resetDailyCompletions()
            loadTasks()
            loadPrayerTimes()
        }
    }

    override fun onPause() {
        super.onPause()
        stopUpcomingTicker()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDetailsIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        currentSort = when (item.itemId) {
            R.id.action_sort_category -> {
                Toast.makeText(this, R.string.sorted_by_category, Toast.LENGTH_SHORT).show()
                SortType.CATEGORY
            }
            R.id.action_sort_priority -> {
                Toast.makeText(this, R.string.sorted_by_priority, Toast.LENGTH_SHORT).show()
                SortType.PRIORITY
            }
            R.id.action_sort_time -> {
                Toast.makeText(this, R.string.sorted_by_time, Toast.LENGTH_SHORT).show()
                SortType.TIME
            }
            else -> return super.onOptionsItemSelected(item)
        }
        renderTaskSections()
        return true
    }

    fun closeAddEditFragment(refreshTasks: Boolean = true) {
        supportFragmentManager.findFragmentById(R.id.addEditContainer)?.let { fragment ->
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        addEditContainer.visibility = View.GONE
        if (refreshTasks) loadTasks()
    }

    private fun setupRecyclerViews() {
        summaryAdapter = SummaryAdapter()
        summaryRecyclerView = findViewById(R.id.summaryRecyclerView)
        summaryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = summaryAdapter
        }
        updateSummary()
    }

    private fun applySummaryVisibility() {
        val enabled = PreferencesHelper.isSummarySectionEnabled(this)
        val expanded = PreferencesHelper.isSectionVisible(this, SECTION_SUMMARY)
        summarySection.visibility = if (enabled) View.VISIBLE else View.GONE
        summaryRecyclerView.visibility = if (enabled && expanded) View.VISIBLE else View.GONE
    }

    private fun applyUpcomingVisibility() {
        upcomingSection.visibility =
            if (PreferencesHelper.isUpcomingSectionEnabled(this)) View.VISIBLE else View.GONE
        renderUpcomingTask()
    }

    private fun updateToggleIcon(button: MaterialButton, visible: Boolean) {
        button.text = ""
        button.setIconResource(if (visible) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)
    }

    private fun resetDailyCompletions() {
        PreferencesHelper.resetDailyCompletionsIfNeeded(this, DateTimeHelper.localDateKey())
    }

    private fun setupBottomNavigation() {
        NavigationHelper.setupBottomNavigation(
            this,
            findViewById(R.id.bottomNavigation),
            R.id.nav_home,
            "home"
        )
    }

    private fun setupAddButton() {
        findViewById<FloatingActionButton>(R.id.addTaskFab).setOnClickListener {
            showAddEditFragment(null)
        }
    }

    private fun showAddEditFragment(taskId: Long?) {
        addEditContainer.visibility = View.VISIBLE
        val fragment = AddEditTaskFragment.newInstance(taskId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.addEditContainer, fragment)
            .commit()
    }

    private fun showTaskDetailsFragment(taskId: Long) {
        addEditContainer.visibility = View.VISIBLE
        val fragment = TaskDetailsFragment.newInstance(taskId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.addEditContainer, fragment)
            .commit()
    }

    private fun handleDetailsIntent(intent: Intent?) {
        if (intent?.getBooleanExtra(EXTRA_OPEN_TASK_DETAILS, false) != true) return

        val taskId = intent.getLongExtra(EXTRA_TASK_ID, -1L)
        if (taskId > 0L) {
            showTaskDetailsFragment(taskId)
        } else {
            Toast.makeText(this, R.string.task_not_found, Toast.LENGTH_SHORT).show()
        }

        intent.removeExtra(EXTRA_OPEN_TASK_DETAILS)
        intent.removeExtra(EXTRA_TASK_ID)
    }

    private fun loadTasks() {
        lifecycleScope.launch {
            val loadedTasks = withContext(Dispatchers.IO) {
                val activeTasks = database.taskDao().getActiveTasks()
                    .filter { DateTimeHelper.shouldShowTaskToday(it) }
                val allTodayTasks = database.taskDao().getAllTasks()
                    .filter { shouldCountTaskToday(it) }
                activeTasks to allTodayTasks
            }
            currentTasks = loadedTasks.first
            todayTasksForSummary = loadedTasks.second
            renderTaskSections()
            updateSummary()
            renderUpcomingTask()
        }
    }

    private fun shouldCountTaskToday(task: TaskEntity): Boolean {
        if (task.isSystemTask) return task.taskDate == DateTimeHelper.localDateKey()
        return DateTimeHelper.shouldShowTaskToday(task)
    }

    private fun renderTaskSections() {
        taskSectionsContainer.removeAllViews()
        val sections = buildTaskSections()
        var hasVisibleSection = false

        sections.filter { it.tasks.isNotEmpty() }.forEach { section ->
            hasVisibleSection = true
            addSectionView(section)
        }

        emptyTasksText.visibility = if (hasVisibleSection) View.GONE else View.VISIBLE
    }

    private fun addSectionView(section: TaskSection) {
        val view = LayoutInflater.from(this).inflate(R.layout.item_task_section, taskSectionsContainer, false)
        val titleText = view.findViewById<TextView>(R.id.sectionTitleText)
        val toggleButton = view.findViewById<MaterialButton>(R.id.sectionToggleButton)
        val recyclerView = view.findViewById<RecyclerView>(R.id.sectionRecyclerView)
        val visible = PreferencesHelper.isSectionVisible(this, section.key)

        titleText.text = section.title
        toggleButton.contentDescription = section.title
        updateToggleIcon(toggleButton, visible)
        recyclerView.visibility = if (visible) View.VISIBLE else View.GONE
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TaskAdapter(
            onTaskClicked = { showTaskDetailsFragment(it.id) },
            onTaskEditClicked = { showAddEditFragment(it.id) },
            onTaskCompleted = { completeTask(it) },
            onTaskDeleted = { deleteTask(it) },
            onAzkarSearchClicked = { openAzkarSearch(it) }
        ).apply {
            submitList(section.tasks)
        }

        toggleButton.setOnClickListener {
            val newVisibility = !PreferencesHelper.isSectionVisible(this, section.key)
            PreferencesHelper.setSectionVisible(this, section.key, newVisibility)
            renderTaskSections()
        }

        taskSectionsContainer.addView(view)
    }

    private fun buildTaskSections(): List<TaskSection> {
        return when (currentSort) {
            SortType.CATEGORY -> buildCategorySections()
            SortType.PRIORITY -> buildPrioritySections()
            SortType.TIME -> buildTimeSections()
        }
    }

    private fun buildCategorySections(): List<TaskSection> {
        return DateTimeHelper.categoryOrder.map { category ->
            val tasks = currentTasks
                .filter { DateTimeHelper.normalizeCategory(it.category) == category }
                .sortedBy { it.timeMillis }
            TaskSection(
                key = "category_$category",
                title = DateTimeHelper.localizedCategory(this, category),
                tasks = tasks
            )
        }
    }

    private fun buildPrioritySections(): List<TaskSection> {
        return listOf(
            Triple(DateTimeHelper.PRIORITY_HIGH, getString(R.string.section_high_priority), "priority_high"),
            Triple(DateTimeHelper.PRIORITY_MEDIUM, getString(R.string.section_medium_priority), "priority_medium"),
            Triple(DateTimeHelper.PRIORITY_LOW, getString(R.string.section_low_priority), "priority_low")
        ).map { (priority, title, key) ->
            TaskSection(
                key = key,
                title = title,
                tasks = currentTasks.filter { it.priority == priority }.sortedBy { it.timeMillis }
            )
        }
    }

    private fun buildTimeSections(): List<TaskSection> {
        val prayerNames = listOf("Fajr", "Dhuhr", "Asr", "Maghrib", "Isha")
        val titles = mapOf(
            "Fajr" to getString(R.string.section_after_fajr),
            "Dhuhr" to getString(R.string.section_after_dhuhr),
            "Asr" to getString(R.string.section_after_asr),
            "Maghrib" to getString(R.string.section_after_maghrib),
            "Isha" to getString(R.string.section_after_isha)
        )
        val grouped = prayerNames.associateWith { mutableListOf<TaskEntity>() }

        currentTasks.forEach { task ->
            grouped.getValue(timeSectionPrayerName(task)).add(task)
        }

        return prayerNames.map { prayerName ->
            TaskSection(
                key = "time_$prayerName",
                title = titles.getValue(prayerName),
                tasks = grouped.getValue(prayerName).sortedWith(
                    compareBy<TaskEntity> { if (isPrayerTaskForSection(it, prayerName)) 0 else 1 }
                        .thenBy { minutesOfDay(it.timeMillis) }
                )
            )
        }
    }

    private fun timeSectionPrayerName(task: TaskEntity): String {
        val prayerTaskName = task.systemTaskType
            ?.takeIf { it.startsWith(SYSTEM_PRAYER_PREFIX) }
            ?.removePrefix(SYSTEM_PRAYER_PREFIX)
            ?.let { DateTimeHelper.canonicalPrayerName(it) }
        if (prayerTaskName in PRAYER_NAMES) return prayerTaskName ?: "Isha"

        val fajr = prayerMinutes("Fajr") ?: return "Isha"
        val dhuhr = prayerMinutes("Dhuhr") ?: return "Isha"
        val asr = prayerMinutes("Asr") ?: return "Isha"
        val maghrib = prayerMinutes("Maghrib") ?: return "Isha"
        val isha = prayerMinutes("Isha") ?: return "Isha"
        val taskMinutes = minutesOfDay(task.timeMillis)

        return when (taskMinutes) {
            in fajr..<dhuhr -> "Fajr"
            in dhuhr..<asr -> "Dhuhr"
            in asr..<maghrib -> "Asr"
            in maghrib..<isha -> "Maghrib"
            else -> "Isha"
        }
    }

    private fun prayerMinutes(name: String): Int? {
        return currentPrayerTimesByName[name]?.let { minutesOfDay(it) }
            ?: currentTasks.firstOrNull {
                it.systemTaskType == "$SYSTEM_PRAYER_PREFIX$name" ||
                    (
                        DateTimeHelper.normalizeCategory(it.category) == DateTimeHelper.CATEGORY_PRAYER &&
                            DateTimeHelper.canonicalPrayerName(it.title) == name
                        )
            }?.let { minutesOfDay(it.timeMillis) }
    }

    private fun minutesOfDay(timeMillis: Long): Int {
        val calendar = Calendar.getInstance().apply { this.timeInMillis = timeMillis }
        return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
    }

    private fun isPrayerTaskForSection(task: TaskEntity, prayerName: String): Boolean {
        return task.systemTaskType == "$SYSTEM_PRAYER_PREFIX$prayerName" ||
            (
                DateTimeHelper.normalizeCategory(task.category) == DateTimeHelper.CATEGORY_PRAYER &&
                    DateTimeHelper.canonicalPrayerName(task.title) == prayerName
                )
    }

    private fun completeTask(task: TaskEntity) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                database.taskDao().markTaskCompleted(task.id, System.currentTimeMillis())
                AlarmHelper.cancelTaskAlarms(this@HomeActivity, task.id)
            }
            Toast.makeText(this@HomeActivity, R.string.task_completed, Toast.LENGTH_SHORT).show()
            loadTasks()
        }
    }

    private fun deleteTask(task: TaskEntity) {
        if (DateTimeHelper.isProtectedCategory(task.category)) {
            Toast.makeText(this, R.string.protected_task_message, Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (task.isSystemTask && !task.systemTaskType.isNullOrBlank()) {
                    PreferencesHelper.setSystemTaskDeleted(
                        this@HomeActivity,
                        task.taskDate.ifBlank { DateTimeHelper.localDateKey() },
                        task.systemTaskType,
                        true
                    )
                }
                AlarmHelper.cancelTaskAlarms(this@HomeActivity, task.id)
                database.taskDao().deleteTask(task)
            }
            Toast.makeText(this@HomeActivity, R.string.task_deleted_successfully, Toast.LENGTH_SHORT).show()
            loadTasks()
        }
    }

    private fun loadPrayerTimes() {
        lifecycleScope.launch {
            val latitude = PreferencesHelper.getLatitude(this@HomeActivity)
            val longitude = PreferencesHelper.getLongitude(this@HomeActivity)
            val date = DateTimeHelper.apiDate()
            var usedCachedData = false

            val prayerTime = withContext(Dispatchers.IO) {
                val dao = database.prayerDao()
                val internetAvailable = NetworkHelper.isInternetAvailable(this@HomeActivity)
                val cachedToday = dao.getPrayerTimesByDate(date, latitude, longitude)
                if (cachedToday != null) {
                    usedCachedData = !internetAvailable
                    cachedToday
                } else if (internetAvailable) {
                    val fetched = PrayerApiHelper.fetchPrayerTimes(date, latitude, longitude)
                    if (fetched != null) {
                        dao.insertOrUpdatePrayerTimes(fetched)
                        dao.deleteOldPrayerTimes(System.currentTimeMillis() - 8L * 24L * 60L * 60L * 1000L)
                    }
                    fetched
                } else {
                    usedCachedData = true
                    dao.getLatestPrayerTimes()?.copy(date = date)
                }
            }

            if (usedCachedData && prayerTime != null) {
                Toast.makeText(this@HomeActivity, R.string.no_internet_using_cache, Toast.LENGTH_SHORT).show()
            }

            if (prayerTime == null) {
                currentPrayerTimesByName = emptyMap()
                Toast.makeText(this@HomeActivity, R.string.no_internet_no_cache, Toast.LENGTH_SHORT).show()
            } else {
                currentPrayerTimesByName = prayerTimesByName(prayerTime)
                withContext(Dispatchers.IO) {
                    syncGeneratedTasks(prayerTime)
                }
            }
            loadTasks()
        }
    }

    private fun prayerTimesByName(prayerTime: PrayerTimeEntity): Map<String, Long> {
        return mapOf(
            "Fajr" to DateTimeHelper.prayerTimeMillis(prayerTime.date, prayerTime.fajr),
            "Dhuhr" to DateTimeHelper.prayerTimeMillis(prayerTime.date, prayerTime.dhuhr),
            "Asr" to DateTimeHelper.prayerTimeMillis(prayerTime.date, prayerTime.asr),
            "Maghrib" to DateTimeHelper.prayerTimeMillis(prayerTime.date, prayerTime.maghrib),
            "Isha" to DateTimeHelper.prayerTimeMillis(prayerTime.date, prayerTime.isha)
        )
    }

    private suspend fun syncGeneratedTasks(prayerTime: PrayerTimeEntity) {
        val dateKey = DateTimeHelper.localDateKey()
        val prayerTimes = prayerTimesByName(prayerTime)
        val specs = listOf(
            GeneratedTaskSpec("Fajr", DateTimeHelper.CATEGORY_PRAYER, DateTimeHelper.PRIORITY_HIGH, prayerTimes["Fajr"] ?: 0L, "Prayer:Fajr"),
            GeneratedTaskSpec("Dhuhr", DateTimeHelper.CATEGORY_PRAYER, DateTimeHelper.PRIORITY_HIGH, prayerTimes["Dhuhr"] ?: 0L, "Prayer:Dhuhr"),
            GeneratedTaskSpec("Asr", DateTimeHelper.CATEGORY_PRAYER, DateTimeHelper.PRIORITY_HIGH, prayerTimes["Asr"] ?: 0L, "Prayer:Asr"),
            GeneratedTaskSpec("Maghrib", DateTimeHelper.CATEGORY_PRAYER, DateTimeHelper.PRIORITY_HIGH, prayerTimes["Maghrib"] ?: 0L, "Prayer:Maghrib"),
            GeneratedTaskSpec("Isha", DateTimeHelper.CATEGORY_PRAYER, DateTimeHelper.PRIORITY_HIGH, prayerTimes["Isha"] ?: 0L, "Prayer:Isha"),
            GeneratedTaskSpec("Morning Azkar", DateTimeHelper.CATEGORY_AZKAR, DateTimeHelper.PRIORITY_MEDIUM, offsetTime(prayerTimes["Fajr"], TWENTY_MINUTES), "Azkar:Morning"),
            GeneratedTaskSpec("Evening Azkar", DateTimeHelper.CATEGORY_AZKAR, DateTimeHelper.PRIORITY_MEDIUM, offsetTime(prayerTimes["Asr"], TWENTY_MINUTES), "Azkar:Evening"),
            GeneratedTaskSpec("Sleep Azkar", DateTimeHelper.CATEGORY_AZKAR, DateTimeHelper.PRIORITY_MEDIUM, offsetTime(prayerTimes["Isha"], TWO_HOURS), "Azkar:Sleep")
        )

        specs.filter { it.timeMillis > 0L }.forEach { spec ->
            if (PreferencesHelper.isSystemTaskDeleted(this, dateKey, spec.systemTaskType)) return@forEach

            val existingTask = database.taskDao().getSystemTaskForDate(dateKey, spec.systemTaskType)
                ?: database.taskDao().getTaskByDateCategoryAndTitle(dateKey, spec.category, spec.title)
            val now = System.currentTimeMillis()

            if (existingTask == null) {
                val task = TaskEntity(
                    title = spec.title,
                    description = null,
                    timeMillis = spec.timeMillis,
                    category = spec.category,
                    customCategory = null,
                    priority = spec.priority,
                    recurrenceType = DateTimeHelper.RECURRENCE_DAILY,
                    selectedDaysCsv = "ALL",
                    isCompleted = false,
                    completedAtMillis = null,
                    createdAtMillis = now,
                    updatedAtMillis = now,
                    notificationEnabled = true,
                    taskDate = dateKey,
                    isSystemTask = true,
                    systemTaskType = spec.systemTaskType
                )
                val newId = database.taskDao().insertTask(task)
                AlarmHelper.scheduleTaskAlarms(this, task.copy(id = newId))
            } else {
                val shouldUpdateTime = !existingTask.isCompleted && existingTask.timeMillis != spec.timeMillis
                val shouldFillSystemFields = !existingTask.isSystemTask || existingTask.systemTaskType.isNullOrBlank()
                if (shouldUpdateTime || shouldFillSystemFields) {
                    AlarmHelper.cancelTaskAlarms(this, existingTask.id)
                    val updatedTask = existingTask.copy(
                        timeMillis = if (shouldUpdateTime) spec.timeMillis else existingTask.timeMillis,
                        taskDate = dateKey,
                        isSystemTask = true,
                        systemTaskType = spec.systemTaskType,
                        updatedAtMillis = now
                    )
                    database.taskDao().updateTask(updatedTask)
                    AlarmHelper.scheduleTaskAlarms(this, updatedTask)
                } else {
                    AlarmHelper.scheduleTaskAlarms(this, existingTask)
                }
            }
        }
    }

    private fun offsetTime(baseTimeMillis: Long?, offsetMillis: Long): Long {
        return baseTimeMillis?.takeIf { it > 0L }?.plus(offsetMillis) ?: 0L
    }

    private fun updateSummary() {
        fun progressText(tasks: List<TaskEntity>): String {
            val completed = tasks.count { it.isCompleted }
            return "$completed/${tasks.size}"
        }

        val prayerTasks = todayTasksForSummary.filter {
            DateTimeHelper.normalizeCategory(it.category) == DateTimeHelper.CATEGORY_PRAYER
        }
        val azkarTasks = todayTasksForSummary.filter {
            DateTimeHelper.normalizeCategory(it.category) == DateTimeHelper.CATEGORY_AZKAR
        }
        val normalTasks = todayTasksForSummary.filter {
            !DateTimeHelper.isProtectedCategory(it.category)
        }

        summaryAdapter.submitList(
            listOf(
                SummaryItem(R.string.summary_tasks, progressText(normalTasks), R.color.surface_light),
                SummaryItem(R.string.summary_athkar, progressText(azkarTasks), R.color.surface_light),
                SummaryItem(R.string.summary_prayers, progressText(prayerTasks), R.color.surface_light)
            )
        )
    }

    private fun renderUpcomingTask() {
        if (!::upcomingSection.isInitialized) return

        if (!PreferencesHelper.isUpcomingSectionEnabled(this)) {
            upcomingSection.visibility = View.GONE
            return
        }

        upcomingSection.visibility = View.VISIBLE
        val now = System.currentTimeMillis()
        val upcomingTask = currentTasks
            .filter { !it.isCompleted && it.timeMillis >= now }
            .minByOrNull { it.timeMillis }

        if (upcomingTask == null) {
            upcomingCard.visibility = View.GONE
            upcomingEmptyText.visibility = View.VISIBLE
            return
        }

        upcomingCard.visibility = View.VISIBLE
        upcomingEmptyText.visibility = View.GONE
        upcomingTitleText.text = DateTimeHelper.localizedTaskTitle(this, upcomingTask)
        upcomingTimeText.text = DateTimeHelper.displayTime(this, upcomingTask.timeMillis)
        upcomingCountdownText.text = DateTimeHelper.countdownText(this, upcomingTask.timeMillis, now)
        upcomingCard.setOnClickListener { showTaskDetailsFragment(upcomingTask.id) }
    }

    private fun startUpcomingTicker() {
        upcomingHandler.removeCallbacks(upcomingRefreshRunnable)
        upcomingHandler.post(upcomingRefreshRunnable)
    }

    private fun stopUpcomingTicker() {
        upcomingHandler.removeCallbacks(upcomingRefreshRunnable)
    }

    @SuppressLint("UseKtx")
    private fun openAzkarSearch(task: TaskEntity) {
        val query = when (task.systemTaskType) {
            "Azkar:Morning" -> "Morning Azkar"
            "Azkar:Evening" -> "Evening Azkar"
            "Azkar:Sleep" -> "Sleep Azkar"
            else -> task.title
        }
        val url = "https://www.google.com/search?q=${Uri.encode(query)}"
        val urlIntent = Intent(Intent.ACTION_VIEW, url.toUri())
        if (urlIntent.resolveActivity(packageManager) != null) {
            startActivity(urlIntent)
        } else {
            Toast.makeText(this, R.string.external_app_missing, Toast.LENGTH_SHORT).show()
        }
    }

    private data class TaskSection(
        val key: String,
        val title: String,
        val tasks: List<TaskEntity>
    )

    private data class GeneratedTaskSpec(
        val title: String,
        val category: String,
        val priority: String,
        val timeMillis: Long,
        val systemTaskType: String
    )

    private enum class SortType {
        CATEGORY,
        PRIORITY,
        TIME
    }

    private companion object {
        const val SECTION_SUMMARY = "summary"
        const val EXTRA_OPEN_TASK_DETAILS = "openTaskDetails"
        const val EXTRA_TASK_ID = "taskId"
        const val UPCOMING_REFRESH_DELAY = 60_000L
        const val SYSTEM_PRAYER_PREFIX = "Prayer:"
        val PRAYER_NAMES = setOf("Fajr", "Dhuhr", "Asr", "Maghrib", "Isha")
        const val TWENTY_MINUTES = 20L * 60L * 1000L
        const val TWO_HOURS = 2L * 60L * 60L * 1000L
    }
}
