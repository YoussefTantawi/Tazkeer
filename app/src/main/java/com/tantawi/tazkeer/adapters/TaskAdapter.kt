package com.tantawi.tazkeer.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tantawi.tazkeer.R
import com.tantawi.tazkeer.database.TaskEntity
import com.tantawi.tazkeer.helpers.DateTimeHelper

// Adapter connects the task list data to RecyclerView item views.
class TaskAdapter(
    private val onTaskClicked: (TaskEntity) -> Unit,
    private val onTaskEditClicked: (TaskEntity) -> Unit,
    private val onTaskCompleted: (TaskEntity) -> Unit,
    private val onTaskDeleted: (TaskEntity) -> Unit,
    private val onAzkarSearchClicked: (TaskEntity) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val tasks = mutableListOf<TaskEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newTasks: List<TaskEntity>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        val context = holder.itemView.context

        holder.titleText.text = DateTimeHelper.localizedTaskTitle(context, task)
        holder.descriptionText.text = task.description.orEmpty()
        holder.descriptionText.visibility = if (task.description.isNullOrBlank()) View.GONE else View.VISIBLE
        holder.timeText.text = DateTimeHelper.displayTime(context, task.timeMillis)
        holder.categoryText.text = DateTimeHelper.localizedCategory(context, task.category, task.customCategory)
        holder.categoryIcon.setImageResource(DateTimeHelper.categoryIconRes(task.category))
        holder.priorityText.text = DateTimeHelper.localizedPriority(context, task.priority)
        holder.priorityText.setBackgroundResource(priorityBackground(task.priority))
        holder.card.setCardBackgroundColor(ContextCompat.getColor(context, priorityCardColor(task.priority)))

        val isAzkarTask = DateTimeHelper.normalizeCategory(task.category) == DateTimeHelper.CATEGORY_AZKAR
        val isProtectedTask = DateTimeHelper.isProtectedCategory(task.category)
        holder.azkarSearchButton.visibility = if (isAzkarTask) View.VISIBLE else View.GONE
        holder.editButton.visibility = if (isProtectedTask) View.GONE else View.VISIBLE
        holder.deleteButton.visibility = if (isProtectedTask) View.GONE else View.VISIBLE

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = task.isCompleted
        holder.checkBox.setOnClickListener { onTaskCompleted(task) }
        holder.card.setOnClickListener { onTaskClicked(task) }
        holder.editButton.setOnClickListener { onTaskEditClicked(task) }
        holder.deleteButton.setOnClickListener { onTaskDeleted(task) }
        holder.azkarSearchButton.setOnClickListener { onAzkarSearchClicked(task) }
    }

    override fun getItemCount(): Int = tasks.size

    private fun priorityBackground(priority: String): Int {
        return when (priority) {
            DateTimeHelper.PRIORITY_HIGH -> R.drawable.bg_priority_high
            DateTimeHelper.PRIORITY_MEDIUM -> R.drawable.bg_priority_medium
            else -> R.drawable.bg_priority_low
        }
    }

    private fun priorityCardColor(priority: String): Int {
        return R.color.surface_light
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.taskCard)
        val checkBox: CheckBox = itemView.findViewById(R.id.taskCheckBox)
        val titleText: TextView = itemView.findViewById(R.id.taskTitleText)
        val descriptionText: TextView = itemView.findViewById(R.id.taskDescriptionText)
        val timeText: TextView = itemView.findViewById(R.id.taskTimeText)
        val categoryIcon: ImageView = itemView.findViewById(R.id.taskCategoryIcon)
        val categoryText: TextView = itemView.findViewById(R.id.taskCategoryText)
        val priorityText: TextView = itemView.findViewById(R.id.taskPriorityText)
        val azkarSearchButton: ImageButton = itemView.findViewById(R.id.taskAzkarSearchButton)
        val editButton: ImageButton = itemView.findViewById(R.id.taskEditButton)
        val deleteButton: ImageButton = itemView.findViewById(R.id.taskDeleteButton)
    }
}
