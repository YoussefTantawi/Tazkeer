package com.tantawi.tazkeer

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.tantawi.tazkeer.adapters.CompletedTaskAdapter
import com.tantawi.tazkeer.database.AppDatabase
import com.tantawi.tazkeer.database.TaskEntity
import com.tantawi.tazkeer.helpers.AlarmHelper
import com.tantawi.tazkeer.helpers.DateTimeHelper
import com.tantawi.tazkeer.helpers.LanguageHelper
import com.tantawi.tazkeer.helpers.NavigationHelper
import com.tantawi.tazkeer.helpers.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Activity that shows completed user tasks grouped by day.
class CompletedTasksActivity : AppCompatActivity() {
    private lateinit var adapter: CompletedTaskAdapter
    private lateinit var emptyText: TextView
    private lateinit var database: AppDatabase

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.wrapContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        PreferencesHelper.applyTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_tasks)

        database = AppDatabase.getDatabase(this)
        setSupportActionBar(findViewById<MaterialToolbar>(R.id.completedToolbar))

        emptyText = findViewById(R.id.emptyCompletedText)
        adapter = CompletedTaskAdapter { undoCompletedTask(it) }
        findViewById<RecyclerView>(R.id.completedRecyclerView).apply {
            layoutManager = LinearLayoutManager(this@CompletedTasksActivity)
            adapter = this@CompletedTasksActivity.adapter
        }

        NavigationHelper.setupBottomNavigation(
            this,
            findViewById(R.id.completedBottomNavigation),
            R.id.nav_completed,
            "completed"
        )
    }

    override fun onResume() {
        super.onResume()
        loadCompletedTasks()
    }

    private fun loadCompletedTasks() {
        lifecycleScope.launch {
            val tasks = withContext(Dispatchers.IO) {
                database.taskDao().getCompletedTasks()
            }
            adapter.submitTasks(this@CompletedTasksActivity, tasks)
            emptyText.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun undoCompletedTask(task: TaskEntity) {
        lifecycleScope.launch {
            val appContext = applicationContext
            withContext(Dispatchers.IO) {
                val now = System.currentTimeMillis()
                val newTime = if (task.timeMillis <= now) {
                    DateTimeHelper.nextOccurrenceAfter(task, now)
                } else {
                    task.timeMillis
                }
                val updatedTask = task.copy(
                    timeMillis = newTime,
                    isCompleted = false,
                    completedAtMillis = null,
                    updatedAtMillis = now
                )
                database.taskDao().updateTask(updatedTask)
                if (PreferencesHelper.areNotificationsEnabled(appContext)) {
                    AlarmHelper.scheduleTaskAlarms(appContext, updatedTask)
                }
            }
            Toast.makeText(this@CompletedTasksActivity, R.string.task_restored, Toast.LENGTH_SHORT).show()
            loadCompletedTasks()
        }
    }
}
