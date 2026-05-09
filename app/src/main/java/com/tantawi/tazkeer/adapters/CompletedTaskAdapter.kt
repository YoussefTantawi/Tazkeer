package com.tantawi.tazkeer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tantawi.tazkeer.R
import com.tantawi.tazkeer.database.TaskEntity
import com.tantawi.tazkeer.helpers.DateTimeHelper

// Adapter shows completed tasks and date headers in one RecyclerView.
class CompletedTaskAdapter(
    private val onTaskUnchecked: (TaskEntity) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<CompletedListItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitTasks(context: Context, tasks: List<TaskEntity>) {
        items.clear()
        var lastHeader = ""
        tasks.forEach { task ->
            val header = DateTimeHelper.displayDateHeader(context, task.completedAtMillis ?: task.updatedAtMillis)
            if (header != lastHeader) {
                items.add(CompletedListItem.Header(header))
                lastHeader = header
            }
            items.add(CompletedListItem.Task(task))
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CompletedListItem.Header -> VIEW_TYPE_HEADER
            is CompletedListItem.Task -> VIEW_TYPE_TASK
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_completed_day_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_completed_task, parent, false)
            TaskViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is CompletedListItem.Header -> (holder as HeaderViewHolder).dateText.text = item.dateText
            is CompletedListItem.Task -> bindTask(holder as TaskViewHolder, item.task)
        }
    }

    override fun getItemCount(): Int = items.size

    private fun bindTask(holder: TaskViewHolder, task: TaskEntity) {
        val context = holder.itemView.context
        holder.titleText.text = DateTimeHelper.localizedTaskTitle(context, task)
        holder.descriptionText.text = task.description.orEmpty()
        holder.descriptionText.visibility = if (task.description.isNullOrBlank()) View.GONE else View.VISIBLE
        holder.timeText.text = DateTimeHelper.displayTime(context, task.timeMillis)
        holder.categoryText.text = DateTimeHelper.localizedCategory(context, task.category, task.customCategory)
        holder.categoryIcon.setImageResource(DateTimeHelper.categoryIconRes(task.category))
        holder.priorityText.text = DateTimeHelper.localizedPriority(context, task.priority)
        holder.priorityText.setBackgroundResource(DateTimeHelper.priorityBackgroundRes(task.priority))
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = true
        holder.checkBox.setOnClickListener { onTaskUnchecked(task) }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateText: TextView = itemView.findViewById(R.id.completedHeaderText)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.completedTaskCheckBox)
        val titleText: TextView = itemView.findViewById(R.id.completedTaskTitleText)
        val descriptionText: TextView = itemView.findViewById(R.id.completedTaskDescriptionText)
        val timeText: TextView = itemView.findViewById(R.id.completedTaskTimeText)
        val categoryIcon: ImageView = itemView.findViewById(R.id.completedTaskCategoryIcon)
        val categoryText: TextView = itemView.findViewById(R.id.completedTaskCategoryText)
        val priorityText: TextView = itemView.findViewById(R.id.completedTaskPriorityText)
    }

    sealed class CompletedListItem {
        data class Header(val dateText: String) : CompletedListItem()
        data class Task(val task: TaskEntity) : CompletedListItem()
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_TASK = 2
    }
}
