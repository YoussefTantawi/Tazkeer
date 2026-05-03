package com.tantawi.tazkeer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.tantawi.tazkeer.HomeActivity
import com.tantawi.tazkeer.R
import com.tantawi.tazkeer.database.AppDatabase
import com.tantawi.tazkeer.database.TaskEntity
import com.tantawi.tazkeer.helpers.DateTimeHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Read-only screen for showing task information without edit or delete actions.
class TaskDetailsFragment : Fragment() {
    private lateinit var database: AppDatabase
    private var taskId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskId = arguments?.getLong(ARG_TASK_ID, 0L) ?: 0L
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_task_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDatabase.getDatabase(requireContext())

        view.findViewById<MaterialButton>(R.id.closeDetailsButton).setOnClickListener {
            (activity as? HomeActivity)?.closeAddEditFragment(refreshTasks = false)
        }

        if (taskId <= 0L) {
            Toast.makeText(requireContext(), R.string.task_not_found, Toast.LENGTH_SHORT).show()
            (activity as? HomeActivity)?.closeAddEditFragment(refreshTasks = false)
            return
        }

        loadTask()
    }

    private fun loadTask() {
        viewLifecycleOwner.lifecycleScope.launch {
            val task = withContext(Dispatchers.IO) { database.taskDao().getTaskById(taskId) }
            if (task == null) {
                Toast.makeText(requireContext(), R.string.task_not_found, Toast.LENGTH_SHORT).show()
                (activity as? HomeActivity)?.closeAddEditFragment(refreshTasks = false)
                return@launch
            }
            bindTask(task)
        }
    }

    private fun bindTask(task: TaskEntity) {
        val view = requireView()
        view.findViewById<TextView>(R.id.detailsTitleText).text = DateTimeHelper.localizedTaskTitle(requireContext(), task)
        view.findViewById<TextView>(R.id.detailsDescriptionText).text =
            task.description?.takeIf { it.isNotBlank() } ?: getString(R.string.no_description)
        view.findViewById<TextView>(R.id.detailsTimeText).text = DateTimeHelper.displayTime(requireContext(), task.timeMillis)
        view.findViewById<TextView>(R.id.detailsCategoryText).text =
            DateTimeHelper.localizedCategory(requireContext(), task.category, task.customCategory)
        view.findViewById<TextView>(R.id.detailsPriorityText).text = DateTimeHelper.localizedPriority(requireContext(), task.priority)
        view.findViewById<TextView>(R.id.detailsRecurrenceText).text =
            DateTimeHelper.localizedRecurrence(requireContext(), task.recurrenceType)
    }

    companion object {
        private const val ARG_TASK_ID = "taskId"

        fun newInstance(taskId: Long): TaskDetailsFragment {
            return TaskDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_TASK_ID, taskId)
                }
            }
        }
    }
}
