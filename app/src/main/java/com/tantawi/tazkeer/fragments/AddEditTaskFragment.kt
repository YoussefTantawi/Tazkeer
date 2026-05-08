package com.tantawi.tazkeer.fragments

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tantawi.tazkeer.HomeActivity
import com.tantawi.tazkeer.R
import com.tantawi.tazkeer.database.AppDatabase
import com.tantawi.tazkeer.database.TaskEntity
import com.tantawi.tazkeer.helpers.AlarmHelper
import com.tantawi.tazkeer.helpers.DateTimeHelper
import com.tantawi.tazkeer.helpers.PermissionHelper
import com.tantawi.tazkeer.helpers.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

// The only Fragment in the app; it adds and edits user tasks.
class AddEditTaskFragment : Fragment() {
    private lateinit var database: AppDatabase
    private lateinit var titleInputLayout: TextInputLayout
    private lateinit var titleEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var timeButton: MaterialButton
    private lateinit var categorySpinner: Spinner
    private lateinit var customCategoryLayout: TextInputLayout
    private lateinit var customCategoryEditText: TextInputEditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var recurrenceSpinner: Spinner
    private lateinit var customDaysContainer: View
    private lateinit var saveTaskButton: MaterialButton
    private lateinit var dayCheckBoxes: Map<String, CheckBox>

    private var taskId: Long? = null
    private var editingTask: TaskEntity? = null
    private var selectedHour: Int? = null
    private var selectedMinute: Int? = null

    private val categoryValues = listOf(
        DateTimeHelper.CATEGORY_WORK,
        DateTimeHelper.CATEGORY_STUDY,
        DateTimeHelper.CATEGORY_PRAYER,
        DateTimeHelper.CATEGORY_AZKAR,
        DateTimeHelper.CATEGORY_FAMILY,
        DateTimeHelper.CATEGORY_PERSONAL,
        DateTimeHelper.CATEGORY_HEALTH,
        DateTimeHelper.CATEGORY_FITNESS,
        DateTimeHelper.CATEGORY_OTHER
    )
    private val priorityValues = listOf(
        DateTimeHelper.PRIORITY_HIGH,
        DateTimeHelper.PRIORITY_MEDIUM,
        DateTimeHelper.PRIORITY_LOW
    )
    private val recurrenceValues = listOf(
        DateTimeHelper.RECURRENCE_ONCE,
        DateTimeHelper.RECURRENCE_DAILY,
        DateTimeHelper.RECURRENCE_CUSTOM
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argumentId = arguments?.getLong(ARG_TASK_ID, 0L) ?: 0L
        taskId = if (argumentId > 0L) argumentId else null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_add_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDatabase.getDatabase(requireContext())
        bindViews(view)
        setupSpinners()
        setupButtons()

        if (savedInstanceState != null) {
            if (taskId != null) {
                view.findViewById<TextView>(R.id.fragmentTitleText).setText(R.string.edit_task)
                saveTaskButton.isEnabled = false
                loadTaskForEditing(taskId ?: return, fillFormAfterLoad = false)
            }
            restoreFormState(savedInstanceState)
        } else if (taskId == null) {
            setDefaultTime()
        } else {
            view.findViewById<TextView>(R.id.fragmentTitleText).setText(R.string.edit_task)
            saveTaskButton.isEnabled = false
            loadTaskForEditing(taskId ?: return)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!::titleEditText.isInitialized) return

        outState.putString(STATE_TITLE, titleEditText.text?.toString().orEmpty())
        outState.putString(STATE_DESCRIPTION, descriptionEditText.text?.toString().orEmpty())
        outState.putString(STATE_CUSTOM_CATEGORY, customCategoryEditText.text?.toString().orEmpty())
        outState.putInt(STATE_CATEGORY_POSITION, categorySpinner.selectedItemPosition)
        outState.putInt(STATE_PRIORITY_POSITION, prioritySpinner.selectedItemPosition)
        outState.putInt(STATE_RECURRENCE_POSITION, recurrenceSpinner.selectedItemPosition)
        selectedHour?.let { outState.putInt(STATE_SELECTED_HOUR, it) }
        selectedMinute?.let { outState.putInt(STATE_SELECTED_MINUTE, it) }
        outState.putStringArrayList(
            STATE_SELECTED_DAYS,
            ArrayList(dayCheckBoxes.filterValues { it.isChecked }.keys)
        )
    }

    private fun bindViews(view: View) {
        titleInputLayout = view.findViewById(R.id.titleInputLayout)
        titleEditText = view.findViewById(R.id.titleEditText)
        descriptionEditText = view.findViewById(R.id.descriptionEditText)
        timeButton = view.findViewById(R.id.timeButton)
        categorySpinner = view.findViewById(R.id.categorySpinner)
        customCategoryLayout = view.findViewById(R.id.customCategoryLayout)
        customCategoryEditText = view.findViewById(R.id.customCategoryEditText)
        prioritySpinner = view.findViewById(R.id.prioritySpinner)
        recurrenceSpinner = view.findViewById(R.id.recurrenceSpinner)
        customDaysContainer = view.findViewById(R.id.customDaysContainer)
        saveTaskButton = view.findViewById(R.id.saveTaskButton)
        dayCheckBoxes = mapOf(
            DateTimeHelper.DAY_SATURDAY to view.findViewById(R.id.saturdayCheckBox),
            DateTimeHelper.DAY_SUNDAY to view.findViewById(R.id.sundayCheckBox),
            DateTimeHelper.DAY_MONDAY to view.findViewById(R.id.mondayCheckBox),
            DateTimeHelper.DAY_TUESDAY to view.findViewById(R.id.tuesdayCheckBox),
            DateTimeHelper.DAY_WEDNESDAY to view.findViewById(R.id.wednesdayCheckBox),
            DateTimeHelper.DAY_THURSDAY to view.findViewById(R.id.thursdayCheckBox),
            DateTimeHelper.DAY_FRIDAY to view.findViewById(R.id.fridayCheckBox)
        )
    }

    private fun setupSpinners() {
        categorySpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_options,
            android.R.layout.simple_spinner_item
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        prioritySpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priority_options,
            android.R.layout.simple_spinner_item
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        recurrenceSpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.recurrence_options,
            android.R.layout.simple_spinner_item
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = categoryValues[position]
                customCategoryLayout.visibility =
                    if (selectedCategory == DateTimeHelper.CATEGORY_OTHER) View.VISIBLE else View.GONE
                if (editingTask == null) {
                    when (selectedCategory) {
                        DateTimeHelper.CATEGORY_PRAYER -> {
                            prioritySpinner.setSelection(priorityValues.indexOf(DateTimeHelper.PRIORITY_HIGH))
                        }
                        DateTimeHelper.CATEGORY_AZKAR -> {
                            prioritySpinner.setSelection(priorityValues.indexOf(DateTimeHelper.PRIORITY_MEDIUM))
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        recurrenceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                customDaysContainer.visibility =
                    if (recurrenceValues[position] == DateTimeHelper.RECURRENCE_CUSTOM) View.VISIBLE else View.GONE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun setupButtons() {
        timeButton.setOnClickListener { showTimePicker() }
        saveTaskButton.setOnClickListener { saveTask() }
        view?.findViewById<MaterialButton>(R.id.cancelTaskButton)?.setOnClickListener {
            (activity as? HomeActivity)?.closeAddEditFragment(refreshTasks = false)
        }
    }

    private fun setDefaultTime() {
        val now = Calendar.getInstance()
        selectedHour = now.get(Calendar.HOUR_OF_DAY)
        selectedMinute = now.get(Calendar.MINUTE)
        updateTimeButton()
    }

    private fun showTimePicker() {
        val hour = selectedHour ?: Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val minute = selectedMinute ?: Calendar.getInstance().get(Calendar.MINUTE)
        TimePickerDialog(requireContext(), { _, pickedHour, pickedMinute ->
            selectedHour = pickedHour
            selectedMinute = pickedMinute
            updateTimeButton()
        }, hour, minute, android.text.format.DateFormat.is24HourFormat(requireContext())).show()
    }

    private fun updateTimeButton() {
        val hour = selectedHour ?: return
        val minute = selectedMinute ?: return
        val timeMillis = DateTimeHelper.nextTimeMillis(
            hour,
            minute,
            DateTimeHelper.RECURRENCE_ONCE,
            ""
        )
        timeButton.text = getString(
            R.string.task_time_value,
            getString(R.string.task_time),
            DateTimeHelper.displayTime(requireContext(), timeMillis)
        )
    }

    private fun loadTaskForEditing(id: Long, fillFormAfterLoad: Boolean = true) {
        viewLifecycleOwner.lifecycleScope.launch {
            val task = withContext(Dispatchers.IO) { database.taskDao().getTaskById(id) }
            if (task == null) {
                (activity as? HomeActivity)?.closeAddEditFragment(refreshTasks = false)
                return@launch
            }
            if (DateTimeHelper.isProtectedCategory(task.category)) {
                Toast.makeText(requireContext(), R.string.protected_task_message, Toast.LENGTH_SHORT).show()
                (activity as? HomeActivity)?.closeAddEditFragment(refreshTasks = false)
                return@launch
            }
            editingTask = task
            saveTaskButton.isEnabled = true
            if (fillFormAfterLoad) {
                fillForm(task)
            }
        }
    }

    private fun restoreFormState(savedInstanceState: Bundle) {
        titleEditText.setText(savedInstanceState.getString(STATE_TITLE).orEmpty())
        descriptionEditText.setText(savedInstanceState.getString(STATE_DESCRIPTION).orEmpty())
        customCategoryEditText.setText(savedInstanceState.getString(STATE_CUSTOM_CATEGORY).orEmpty())

        if (savedInstanceState.containsKey(STATE_SELECTED_HOUR) && savedInstanceState.containsKey(STATE_SELECTED_MINUTE)) {
            selectedHour = savedInstanceState.getInt(STATE_SELECTED_HOUR)
            selectedMinute = savedInstanceState.getInt(STATE_SELECTED_MINUTE)
            updateTimeButton()
        } else if (taskId == null) {
            setDefaultTime()
        }

        categorySpinner.setSelection(savedInstanceState.safePosition(STATE_CATEGORY_POSITION, categoryValues.lastIndex))
        prioritySpinner.setSelection(savedInstanceState.safePosition(STATE_PRIORITY_POSITION, priorityValues.lastIndex))
        recurrenceSpinner.setSelection(savedInstanceState.safePosition(STATE_RECURRENCE_POSITION, recurrenceValues.lastIndex))

        val selectedDays = savedInstanceState.getStringArrayList(STATE_SELECTED_DAYS)?.toSet().orEmpty()
        dayCheckBoxes.forEach { (day, checkBox) ->
            checkBox.isChecked = selectedDays.contains(day)
        }
    }

    private fun Bundle.safePosition(key: String, lastIndex: Int): Int {
        return getInt(key, 0).coerceIn(0, lastIndex)
    }

    private fun fillForm(task: TaskEntity) {
        titleEditText.setText(task.title)
        descriptionEditText.setText(task.description.orEmpty())

        val calendar = Calendar.getInstance().apply { timeInMillis = task.timeMillis }
        selectedHour = calendar.get(Calendar.HOUR_OF_DAY)
        selectedMinute = calendar.get(Calendar.MINUTE)
        updateTimeButton()

        val normalizedCategory = DateTimeHelper.normalizeCategory(task.category)
        categorySpinner.setSelection(categoryValues.indexOf(normalizedCategory).coerceAtLeast(0))
        if (normalizedCategory == DateTimeHelper.CATEGORY_OTHER) {
            customCategoryEditText.setText(task.customCategory.orEmpty())
        }
        prioritySpinner.setSelection(priorityValues.indexOf(task.priority).coerceAtLeast(0))
        recurrenceSpinner.setSelection(recurrenceValues.indexOf(task.recurrenceType).coerceAtLeast(0))

        val selectedDays = DateTimeHelper.selectedDays(task.selectedDaysCsv)
        dayCheckBoxes.forEach { (day, checkBox) ->
            checkBox.isChecked = selectedDays.contains(day)
        }
    }

    private fun saveTask() {
        titleInputLayout.error = null
        val title = titleEditText.text?.toString()?.trim().orEmpty()
        if (title.isBlank()) {
            titleInputLayout.error = getString(R.string.title_required)
            return
        }

        val hour = selectedHour
        val minute = selectedMinute
        if (hour == null || minute == null) {
            Toast.makeText(requireContext(), R.string.time_required, Toast.LENGTH_SHORT).show()
            return
        }

        val category = categoryValues[categorySpinner.selectedItemPosition]
        val customCategory = customCategoryEditText.text?.toString()?.trim().orEmpty()
        if (category == DateTimeHelper.CATEGORY_OTHER && customCategory.isBlank()) {
            customCategoryLayout.error = getString(R.string.custom_category_required)
            return
        }
        customCategoryLayout.error = null

        val priority = priorityValues[prioritySpinner.selectedItemPosition]
        var recurrence = recurrenceValues[recurrenceSpinner.selectedItemPosition]
        val selectedDays = dayCheckBoxes.filterValues { it.isChecked }.keys
        var selectedDaysCsv = ""

        if (recurrence == DateTimeHelper.RECURRENCE_DAILY) {
            selectedDaysCsv = "ALL"
        } else if (recurrence == DateTimeHelper.RECURRENCE_CUSTOM) {
            if (selectedDays.isEmpty()) {
                Toast.makeText(requireContext(), R.string.custom_day_required, Toast.LENGTH_SHORT).show()
                return
            }
            selectedDaysCsv = DateTimeHelper.selectedDaysCsv(selectedDays)
            if (selectedDaysCsv == "ALL") {
                recurrence = DateTimeHelper.RECURRENCE_DAILY
                recurrenceSpinner.setSelection(recurrenceValues.indexOf(DateTimeHelper.RECURRENCE_DAILY))
                customDaysContainer.visibility = View.GONE
            }
        }

        val taskTimeMillis = DateTimeHelper.nextTimeMillis(hour, minute, recurrence, selectedDaysCsv)
        val now = System.currentTimeMillis()
        val oldTask = editingTask
        val task = oldTask?.copy(
            title = title,
            description = descriptionEditText.text?.toString()?.trim().takeUnless { it.isNullOrBlank() },
            timeMillis = taskTimeMillis,
            category = category,
            customCategory = customCategory.takeIf { category == DateTimeHelper.CATEGORY_OTHER },
            priority = priority,
            recurrenceType = recurrence,
            selectedDaysCsv = selectedDaysCsv,
            isCompleted = false,
            completedAtMillis = null,
            updatedAtMillis = now,
            notificationEnabled = true,
            taskDate = if (oldTask.isSystemTask) oldTask.taskDate else DateTimeHelper.localDateKey(taskTimeMillis)
        )
            ?: TaskEntity(
                title = title,
                description = descriptionEditText.text?.toString()?.trim().takeUnless { it.isNullOrBlank() },
                timeMillis = taskTimeMillis,
                category = category,
                customCategory = customCategory.takeIf { category == DateTimeHelper.CATEGORY_OTHER },
                priority = priority,
                recurrenceType = recurrence,
                selectedDaysCsv = selectedDaysCsv,
                isCompleted = false,
                completedAtMillis = null,
                createdAtMillis = now,
                updatedAtMillis = now,
                notificationEnabled = true,
                taskDate = DateTimeHelper.localDateKey(taskTimeMillis)
            )

        val appContext = requireContext().applicationContext
        viewLifecycleOwner.lifecycleScope.launch {
            val savedTask = withContext(Dispatchers.IO) {
                if (oldTask == null) {
                    val newId = database.taskDao().insertTask(task)
                    task.copy(id = newId)
                } else {
                    AlarmHelper.cancelTaskAlarms(appContext, oldTask.id)
                    database.taskDao().updateTask(task)
                    task
                }
            }

            if (PreferencesHelper.areNotificationsEnabled(requireContext())) {
                if (PermissionHelper.requestNotificationsIfNeeded(requireActivity())) {
                    val schedulingFailed = withContext(Dispatchers.IO) {
                        runCatching { AlarmHelper.scheduleTaskAlarms(appContext, savedTask) }.isFailure
                    }
                    if (schedulingFailed) {
                        Toast.makeText(requireContext(), R.string.notification_schedule_warning, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            Toast.makeText(
                requireContext(),
                if (oldTask == null) R.string.task_saved_successfully else R.string.task_updated_successfully,
                Toast.LENGTH_SHORT
            ).show()
            (activity as? HomeActivity)?.closeAddEditFragment(refreshTasks = true)
        }
    }

    companion object {
        private const val ARG_TASK_ID = "taskId"
        private const val STATE_TITLE = "title"
        private const val STATE_DESCRIPTION = "description"
        private const val STATE_CUSTOM_CATEGORY = "customCategory"
        private const val STATE_CATEGORY_POSITION = "categoryPosition"
        private const val STATE_PRIORITY_POSITION = "priorityPosition"
        private const val STATE_RECURRENCE_POSITION = "recurrencePosition"
        private const val STATE_SELECTED_HOUR = "selectedHour"
        private const val STATE_SELECTED_MINUTE = "selectedMinute"
        private const val STATE_SELECTED_DAYS = "selectedDays"

        fun newInstance(taskId: Long?): AddEditTaskFragment {
            return AddEditTaskFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_TASK_ID, taskId ?: 0L)
                }
            }
        }
    }
}
